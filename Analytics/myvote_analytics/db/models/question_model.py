from sqlalchemy.orm import Mapped, mapped_column
from sqlalchemy.sql.sqltypes import String

from myvote_analytics.db.base import Base


class QuestionModel(Base):
    """Model for demo purpose."""

    __tablename__ = "question"

    id: Mapped[int] = mapped_column(primary_key=True, autoincrement=True)
    external_id: Mapped[int] = mapped_column()
    file_id: Mapped[int] = mapped_column()
    name: Mapped[str] = mapped_column(String(length=200))  # noqa: WPS432
