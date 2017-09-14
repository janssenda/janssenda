package com.sg.cardsjava;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by kdrudy on 9/12/16.
 */
public class Main {

    private static HashSet<Card> createDeck() {
        HashSet<Card> deck = new HashSet<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                Card c = new Card(suit, rank);
                deck.add(c);
            }
        }
        return deck;
    }

    private static HashSet<HashSet<Card>> createHands(HashSet<Card> deck) {
        long[] count = {0};
        HashSet<HashSet<Card>> hands = new HashSet<>();
        deck.forEach((c1) -> {
            HashSet<Card> deck2 = (HashSet<Card>) deck.clone();
            deck2.remove(c1);
            deck2.forEach((c2) -> {
                HashSet<Card> deck3 = (HashSet<Card>) deck2.clone();
                deck3.remove(c2);
                deck3.forEach((c3) -> {
                    HashSet<Card> deck4 = (HashSet<Card>) deck3.clone();
                    deck4.remove(c3);
                    deck4.stream().map((c4) -> {
                        HashSet<Card> hand = new HashSet();
                        hand.add(c1);
                        hand.add(c2);
                        hand.add(c3);
                        hand.add(c4);
                        
                        return hand;
                    }).forEachOrdered((hand) -> {
                        count[0] = count[0]+1; 
                        hands.add(hand);
                    });
                });
            });
        });
        System.out.println("Count:" + count[0]);
        System.out.println("Hands: " + hands.size());
        return hands;
    }

    public static boolean isFlush(HashSet<Card> hand) {
        Set<Card.Suit> suits = hand.stream()
                .map((c) -> c.getSuit())
                .collect(Collectors.toCollection(HashSet::new));
        return suits.size() == 1;
    }

    public static boolean isStraight(HashSet<Card> hand) {
        List<Card.Rank> ranks = hand.stream()
                .map((c) -> c.getRank())
                .sorted((c1, c2) -> Integer.compare(c1.getVal(), c2.getVal()))
                .collect(Collectors.toCollection(ArrayList::new));

        for (int i = 1; i < ranks.size(); i++) {
            if (ranks.get(i).getVal() - ranks.get(i - 1).getVal() != 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isStraightFlush(HashSet<Card> hand) {
        return isFlush(hand) && isStraight(hand);
    }

    public static boolean isFourOfAKind(HashSet<Card> hand) {
        Set<Card.Rank> ranks = hand.stream()
                .map((c) -> c.getRank())
                .collect(Collectors.toCollection(HashSet::new));
        return ranks.size() == 1;
    }

    public static boolean isThreeOfAKind(HashSet<Card> hand) {
        return hand.stream()
                .map((c) -> c.getRank())
                .collect(Collectors.groupingBy((r) -> r.getVal()))
                .values()
                .stream()
                .anyMatch((v) -> v.size() == 3);
    }

    public static boolean isTwoPair(HashSet<Card> hand) {
        return hand.stream()
                .map((c) -> c.getRank())
                .collect(Collectors.groupingBy((r) -> r.getVal()))
                .values()
                .stream()
                .allMatch((v) -> v.size() == 2);
    }

    public static void main(String args[]) {

        HashSet<Card> deck = createDeck();

        long t0 = System.nanoTime();
        HashSet<HashSet<Card>> allHands = createHands(deck);
        System.out.println("Computation time (ms): " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t0));

//        HashSet<HashSet<Card>> allHands = createHands(deck);
        System.out.println("Total Hands: " + allHands.size());
//        HashSet<HashSet<Card>> straightHands = allHands;
//        straightHands = straightHands.stream()
//                .filter(Main::isStraight)
//                .filter((h) -> !isStraightFlush(h))
//                .collect(Collectors.toCollection(HashSet::new));
//        System.out.println("Straights (2520)" + straightHands.size());
//
//        HashSet<HashSet<Card>> flushHands = allHands;
//        flushHands = flushHands.stream()
//                .filter(Main::isFlush)
//                .filter((h) -> !isStraightFlush(h))
//                .collect(Collectors.toCollection(HashSet::new));
//        System.out.println("Flushes (2820)" + flushHands.size());
//
//        HashSet<HashSet<Card>> straightFlushHands = allHands;
//        straightFlushHands = straightFlushHands.stream()
//                .filter(Main::isStraightFlush)
//                .collect(Collectors.toCollection(HashSet::new));
//        System.out.println("Straight Flushes (40)" + straightFlushHands.size());
//
//        HashSet<HashSet<Card>> fourKindHands = allHands;
//        fourKindHands = fourKindHands.stream()
//                .filter(Main::isFourOfAKind)
//                .collect(Collectors.toCollection(HashSet::new));
//        System.out.println("Four of a kinds (13)" + fourKindHands.size());
//
//        HashSet<HashSet<Card>> threeKindHands = allHands;
//        threeKindHands = threeKindHands.stream()
//                .filter(Main::isThreeOfAKind)
//                .filter((h) -> !isFourOfAKind(h))
//                .collect(Collectors.toCollection(HashSet::new));
//        System.out.println("Three of a kinds (2496)" + threeKindHands.size());
//
//        HashSet<HashSet<Card>> twoPairHands = allHands;
//        twoPairHands = twoPairHands.stream()
//                .filter(Main::isTwoPair)
//                .filter((h) -> !isFourOfAKind(h))
//                .collect(Collectors.toCollection(HashSet::new));
//        System.out.println("Two pairs (2808)" + twoPairHands.size());
    }
}
