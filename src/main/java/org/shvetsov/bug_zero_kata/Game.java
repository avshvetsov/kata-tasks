package org.shvetsov.bug_zero_kata;

import lombok.Getter;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Game {
    public static final int MIN_NUMBER_OF_PLAYERS = 2;

    private final List<Player> players = new ArrayList<>();
    private Player currentPlayer;

    private final Supplier<Integer> rollStepSupplier;
    private final Predicate<Integer> penaltyBoxPredicate;
    private final Supplier<Boolean> correctAnswerCheckerSupplier;
    private final int pursesToWin;
    private final int boardSize;

    private Game(GameBuilder builder) {
        Arrays.stream(builder.names)
                .forEach(this::add);
        this.rollStepSupplier = builder.rollStepSupplier;
        this.penaltyBoxPredicate = builder.penaltyBoxPredicate;
        this.correctAnswerCheckerSupplier = builder.correctAnswerCheckerSupplier;
        this.pursesToWin = builder.pursesToWin;
        this.boardSize = builder.boardSize;
    }

    private int getBoardSize() {
        return boardSize;
    }

    private void add(String playerName) {
        Player player = new Player(playerName);
        players.add(player);
        player.setNumber(players.size());
        System.out.println(player.name + " was added");
        System.out.println("They are player number " + player.number);
    }

    public Player start() {
        if (!isPlayable()) {
            throw new IllegalStateException("Illegal players count: " + players.size() + ". Increase player count up to " + MIN_NUMBER_OF_PLAYERS);
        }
        while (!didPlayerWin()) {
            roll(rollStepSupplier.get());
        }
        return currentPlayer;
    }

    private void roll(int roll) {
        nextPlayer();

        System.out.println(currentPlayer.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            if (penaltyBoxPredicate.test(roll)) {
                currentPlayer.setInPenaltyBox(false);
                System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
                movePlayerAndAskQuestion(roll);
                checkAnswer();
            } else {
                System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
            }
        } else {
            movePlayerAndAskQuestion(roll);
            checkAnswer();
        }
    }

    private boolean isPlayable() {
        return players.size() >= MIN_NUMBER_OF_PLAYERS;
    }

    private void nextPlayer() {
        if (currentPlayer == null) {
            currentPlayer = players.get(0);
        } else this.currentPlayer = players.get((currentPlayer.getNumber() + 1) % players.size());
    }

    private void movePlayerAndAskQuestion(int roll) {
        currentPlayer.setPlace(roll);
        System.out.println(currentPlayer.getName() + "'s new location is " + currentPlayer.getPlace());
        System.out.println("The category is " + currentPlayer.getPlace().getCategory());
        System.out.println(currentPlayer.getPlace().getCategory().getNewQuestion());
    }

    private void checkAnswer() {
        if (correctAnswerCheckerSupplier.get()) {
            System.out.println("Answer was correct!!!!");
            currentPlayer.addPurses();
            System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getPurses() + " Gold Coins.");
        } else {
            System.out.println("Question was incorrectly answered");
            System.out.println(currentPlayer.getName() + " was sent to the penalty box");
            currentPlayer.setInPenaltyBox(true);
        }
    }

    private boolean didPlayerWin() {
        if (currentPlayer == null) {
            return false;
        }
        return currentPlayer.getPurses() == pursesToWin;
    }

    public static class GameBuilder {
        private static final Random random = new Random();
        private static final Supplier<Integer> DEFAULT_ROLL_STEP_SUPPLIER = () -> random.nextInt(5) + 1;
        private static final Predicate<Integer> DEFAULT_PENALTY_BOX_PREDICATE = integer -> integer % 2 != 0;
        private static final Supplier<Boolean> DEFAULT_CORRECT_ANSWER_CHECKER_SUPPLIER = () -> random.nextInt(9) != 7;
        public static final int DEFAULT_PURSES_TO_WIN = 6;
        public static final int DEFAULT_BOARD_SIZE = 12;

        private final String[] names;

        private Supplier<Integer> rollStepSupplier;
        private Predicate<Integer> penaltyBoxPredicate;
        private Supplier<Boolean> correctAnswerCheckerSupplier;
        private int pursesToWin;
        private int boardSize;

        public GameBuilder(String... names) {
            this.names = names;
            this.rollStepSupplier = DEFAULT_ROLL_STEP_SUPPLIER;
            this.penaltyBoxPredicate = DEFAULT_PENALTY_BOX_PREDICATE;
            this.correctAnswerCheckerSupplier = DEFAULT_CORRECT_ANSWER_CHECKER_SUPPLIER;
            this.pursesToWin = DEFAULT_PURSES_TO_WIN;
            this.boardSize = DEFAULT_BOARD_SIZE;
        }

        public GameBuilder rollStepSupplier(Supplier<Integer> supplier) {
            this.rollStepSupplier = supplier;
            return this;
        }

        public GameBuilder penaltyBoxPredicate(Predicate<Integer> predicate) {
            this.penaltyBoxPredicate = predicate;
            return this;
        }

        public GameBuilder correctAnswerCheckerSupplier(Supplier<Boolean> supplier) {
            this.correctAnswerCheckerSupplier = supplier;
            return this;
        }
        public GameBuilder pursesToWin(int purses) {
            this.pursesToWin = purses;
            return this;
        }

        public GameBuilder boardSize(int size) {
            this.boardSize = size;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }

    @Getter
    public class Player {
        private final Place place;
        private int purses;
        private boolean isInPenaltyBox = false;
        private final String name;
        private int number;

        private Player(String name) {
            this.name = name;
            this.place = new Place();
        }

        private void setPlace(int roll) {
            this.place.update(roll);
        }

        private void setNumber(int number) {
            this.number = number;
        }

        private void addPurses() {
            this.purses++;
        }

        private void setInPenaltyBox(boolean inPenaltyBox) {
            isInPenaltyBox = inPenaltyBox;
        }
    }

    private class Place {

        int position;

        private void update(int roll) {
            this.position = (this.position + roll) % Game.this.getBoardSize();
        }

        public QuestionType getCategory() {
            return QuestionType.questionTypeByPosition(position);
        }

        @Override
        public String toString() {
            return String.valueOf(position);
        }
    }

    private enum QuestionType {
        POP("Pop", 0),
        SCIENCE("Science", 1),
        SPORT("Sports", 2),
        ROCK("Rock", 3);

        private static final Map<Integer, QuestionType> QUESTION_TYPE_MAP = Stream.of(values())
                .collect(toMap(questionType -> questionType.typeNumber, e -> e));

        private final String typeName;
        private final int typeNumber;

        private int questionNumber;

        QuestionType(String typeName, int typeNumber) {
            this.typeName = typeName;
            this.typeNumber = typeNumber;
        }

        private String getNewQuestion() {
            return typeName + " Question " + ++questionNumber;
        }

        private static QuestionType questionTypeByPosition(int number) {
            return QUESTION_TYPE_MAP.get(number % values().length);
        }

        @Override
        public String toString() {
            return typeName;
        }
    }
}
