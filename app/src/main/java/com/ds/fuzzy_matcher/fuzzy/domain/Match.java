package com.ds.fuzzy_matcher.fuzzy.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * A generic class to hold the match between 2 objects and the score of the match result.
 * A match between similar Token, Element or Document is represented by this class.
 * <p>
 * The "data" and "matchedWith" object holds the 2 records that matched. And "score" represents the match for these 2 objects.
 * "childScore" is used by ScoringFunction to aggregate and calculate the "score" value
 */
public class Match<T extends Matchable> {


    public Match(T t, T matchedWith) {
        this.data = t;
        this.matchedWith = matchedWith;
    }
    public Match(T t, T matchedWith, List<Score> childScores) {
        this(t, matchedWith);
        List<Score> maxDistinctChildScores = getMaxDistinctScores(childScores);
        setScore(maxDistinctChildScores);
    }

    public Match(T t, T matchedWith, double result) {
        this(t, matchedWith);
        this.score = new Score(result, this);
    }

    private T data;

    private T matchedWith;

    private Score score;

    public T getData() {
        return this.data;
    }

    public T getMatchedWith() {
        return matchedWith;
    }

    public double getResult() {
        return this.score.getResult();
    }

    public Score getScore() {
        return this.score;
    }

    public void setScore(List<Score> childScores) {
        if (this.score == null) {
            this.score = this.data.getScoringFunction().apply(this, childScores);
        }
    }

    private List<Score> getMaxDistinctScores(List<Score> scoreList) {
        Map<Matchable, Optional<Score>> map = scoreList.stream()
                .collect(Collectors.groupingBy(s -> s.getMatch().getData(),
                        Collectors.maxBy(Comparator.comparingDouble(Score::getResult))));

        return map.entrySet().stream().map(entry -> entry.getValue().get()).collect(Collectors.toList());
    }

    public double getWeight() {
        return getData().getWeight();
    }

    @Override
    public String toString() {
        return "{" +
                data +
                ", " + score.getResult() +
                '}';
    }
}
