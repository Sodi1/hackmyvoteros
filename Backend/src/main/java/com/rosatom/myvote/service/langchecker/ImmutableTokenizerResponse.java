package com.rosatom.myvote.service.langchecker;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

@ParametersAreNonnullByDefault
@Immutable
public final class ImmutableTokenizerResponse extends TokenizerResponse {
    private final String original;
    private final Optional<String> corrected;
    private final ImmutableList<String> tokens;

    private static ImmutableTokenizerResponse checkPreconditions(ImmutableTokenizerResponse instance) {
        return instance;
    }

    private ImmutableTokenizerResponse(Builder builder) {
        this.original = builder.original;
        this.corrected = builder.corrected;
        this.tokens = builder.tokensBuilder.build();
    }

    public String original() {
        return this.original;
    }

    public Optional<String> corrected() {
        return this.corrected;
    }

    public ImmutableList<String> tokens() {
        return this.tokens;
    }

    public boolean equals(Object another) {
        return this == another || another instanceof ImmutableTokenizerResponse && this.equalTo((ImmutableTokenizerResponse)another);
    }

    private boolean equalTo(ImmutableTokenizerResponse another) {
        return this.original.equals(another.original) && this.corrected.equals(another.corrected) && this.tokens.equals(another.tokens);
    }

    private int computeHashCode() {
        int h = 31;
        h = h * 17 + this.original.hashCode();
        h = h * 17 + this.corrected.hashCode();
        h = h * 17 + this.tokens.hashCode();
        return h;
    }

    public int hashCode() {
        return this.computeHashCode();
    }

    public static Builder builder() {
        return new Builder();
    }

    @NotThreadSafe
    public static final class Builder {
        private static final String REQUIRED_ATTRIBUTE = "Cannot build TokenizerResponse: required attribute '%s' is not set";
        @Nullable
        private String original;
        private Optional<String> corrected;
        private ImmutableList.Builder<String> tokensBuilder;

        private Builder() {
            this.corrected = Optional.absent();
            this.tokensBuilder = ImmutableList.builder();
        }

        public Builder copy(TokenizerResponse fromInstance) {
            Preconditions.checkNotNull(fromInstance);
            this.original(fromInstance.original());
            Optional<String> optionalCorrected = fromInstance.corrected();
            if (optionalCorrected.isPresent()) {
                this.corrected = optionalCorrected;
            }

            this.addAllTokens(fromInstance.tokens());
            return this;
        }

        public Builder original(String original) {
            this.original = (String)Preconditions.checkNotNull(original);
            return this;
        }

        public Builder corrected(String corrected) {
            this.corrected(Optional.of(corrected));
            return this;
        }

        public Builder corrected(Optional<String> corrected) {
            this.corrected = (Optional)Preconditions.checkNotNull(corrected);
            return this;
        }

        public Builder addTokens(String tokensElement) {
            this.tokensBuilder.add(tokensElement);
            return this;
        }

        public Builder addAllTokens(Iterable<? extends String> tokensElements) {
            this.tokensBuilder.addAll(tokensElements);
            return this;
        }

        public Builder clearTokens() {
            this.tokensBuilder = ImmutableList.builder();
            return this;
        }

        public ImmutableTokenizerResponse build() {
            Preconditions.checkState(this.original != null, "Cannot build TokenizerResponse: required attribute '%s' is not set", new Object[]{"original"});
            return ImmutableTokenizerResponse.checkPreconditions(new ImmutableTokenizerResponse(this));
        }
    }
}

