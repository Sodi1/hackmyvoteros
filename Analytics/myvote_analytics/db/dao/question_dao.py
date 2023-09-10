from typing import List, Optional

from fastapi import Depends
from sqlalchemy import select
from sqlalchemy.ext.asyncio import AsyncSession

from myvote_analytics.db.dependencies import get_db_session
from myvote_analytics.db.models.question_model import QuestionModel
from myvote_analytics.services.sbert_large_mt_ru_retriever.dependencies import similar_sentences

class QuestionDAO:
    """Class for accessing dummy table."""

    def __init__(self, session: AsyncSession = Depends(get_db_session)):
        self.session = session


    async def get_all_questions(self, file_id) -> List[QuestionModel]:
        """
        Get all question models
        :return: stream of questions.
        """
        raw_questions = await self.session.execute(
            select(QuestionModel).where(QuestionModel.file_id == file_id ),
        )

        return list(raw_questions.scalars().fetchall())


