package com.rosatom.myvote.service.langchecker;


import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

@ParametersAreNonnullByDefault
@Immutable
final class ImmutableToken extends Token {
    private final TokenType type;
    private final String original;
    private final String canonical;
    private final String corrected;
    private final CharType.Set charTypes;

    private static ImmutableToken checkPreconditions(ImmutableToken instance) {
        return instance;
    }

    private ImmutableToken(Builder builder) {
        this.type = builder.type;
        this.original = builder.original;
        this.canonical = builder.canonical;
        this.corrected = builder.corrected;
        this.charTypes = builder.charTypes;
    }

    public TokenType type() {
        return this.type;
    }

    public String original() {
        return this.original;
    }

    public String canonical() {
        return this.canonical;
    }

    public String corrected() {
        return this.corrected;
    }

    public CharType.Set charTypes() {
        return this.charTypes;
    }

    public boolean equals(Object another) {
        return this == another || another instanceof ImmutableToken && this.equalTo((ImmutableToken)another);
    }

    private boolean equalTo(ImmutableToken another) {
        return this.type.equals(another.type) && this.original.equals(another.original) && this.canonical.equals(another.canonical) && this.corrected.equals(another.corrected) && this.charTypes.equals(another.charTypes);
    }

    private int computeHashCode() {
        int h = 31;
        h = h * 17 + this.type.hashCode();
        h = h * 17 + this.original.hashCode();
        h = h * 17 + this.canonical.hashCode();
        h = h * 17 + this.corrected.hashCode();
        h = h * 17 + this.charTypes.hashCode();
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
        private static final String REQUIRED_ATTRIBUTE = "Cannot build Token: required attribute '%s' is not set";
        @Nullable
        private TokenType type;
        @Nullable
        private String original;
        @Nullable
        private String canonical;
        @Nullable
        private String corrected;
        @Nullable
        private CharType.Set charTypes;

        private Builder() {
        }

        public Builder copy(Token fromInstance) {
            Preconditions.checkNotNull(fromInstance);
            this.type(fromInstance.type());
            this.original(fromInstance.original());
            this.canonical(fromInstance.canonical());
            this.corrected(fromInstance.corrected());
            this.charTypes(fromInstance.charTypes());
            return this;
        }

        public Builder type(TokenType type) {
            this.type = (TokenType)Preconditions.checkNotNull(type);
            return this;
        }

        public Builder original(String original) {
            this.original = (String)Preconditions.checkNotNull(original);
            return this;
        }

        public Builder canonical(String canonical) {
            this.canonical = (String)Preconditions.checkNotNull(canonical);
            return this;
        }

        public Builder corrected(String corrected) {
            this.corrected = (String)Preconditions.checkNotNull(corrected);
            return this;
        }

        public Builder charTypes(CharType.Set charTypes) {
            this.charTypes = (CharType.Set)Preconditions.checkNotNull(charTypes);
            return this;
        }

        public ImmutableToken build() {
            Preconditions.checkState(this.type != null, "Cannot build Token: required attribute '%s' is not set", new Object[]{"type"});
            Preconditions.checkState(this.original != null, "Cannot build Token: required attribute '%s' is not set", new Object[]{"original"});
            Preconditions.checkState(this.canonical != null, "Cannot build Token: required attribute '%s' is not set", new Object[]{"canonical"});
            Preconditions.checkState(this.corrected != null, "Cannot build Token: required attribute '%s' is not set", new Object[]{"corrected"});
            Preconditions.checkState(this.charTypes != null, "Cannot build Token: required attribute '%s' is not set", new Object[]{"charTypes"});
            return ImmutableToken.checkPreconditions(new ImmutableToken(this));
        }
    }
}
