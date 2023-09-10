from sqlalchemy.orm import DeclarativeBase

from myvote_analytics.db.meta import meta


class Base(DeclarativeBase):
    """Base for all models."""

    metadata = meta
