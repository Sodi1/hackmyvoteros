package com.rosatom.myvote.service.langchecker;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
final class TokenFunctions {
    private TokenFunctions() {
    }

    public static Function<Token, String> corrected() {
        return TokenFunctions.CorrectedFunction.INSTANCE;
    }

    public static Predicate<Token> isWord() {
        return TokenFunctions.IsWordPredicate.INSTANCE;
    }

    private static enum CorrectedFunction implements Function<Token, String> {
        INSTANCE;

        private CorrectedFunction() {
        }

        public String apply(Token input) {
            return input.corrected();
        }

        public String toString() {
            return "Tokens.corrected()";
        }
    }

    private static enum IsWordPredicate implements Predicate<Token> {
        INSTANCE;

        private IsWordPredicate() {
        }

        public boolean apply(Token input) {
            return input.isWord();
        }

        public String toString() {
            return "Tokens.isWord()";
        }
    }
}
