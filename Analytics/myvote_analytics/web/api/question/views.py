from typing import List

from fastapi import APIRouter
from fastapi.param_functions import Depends

from myvote_analytics.db.dao.question_dao import QuestionDAO
from myvote_analytics.db.models.question_model import QuestionModel
from myvote_analytics.web.api.question.schema import QuestionModelDTO, QuestionSearchOutputDTO
from myvote_analytics.services.sbert_large_mt_ru_retriever.dependencies import search_similar_questions
router = APIRouter()


@router.get("/search", response_model=List[QuestionSearchOutputDTO])
async def search_questions  (
    query: str,
    file_id: int,
    significance: float = 0.5,
    question_dao: QuestionDAO = Depends(),
) -> List[QuestionSearchOutputDTO]:
    """
    Семантический поиск по вопросам

    :param query: Поисковый запрос.
    :param significance: Отсекает вопросы ниже этой отметки
    :param question_dao: DAO for questions.
    :return: Список найденых вопросов.
    """
    questions = await question_dao.get_all_questions(file_id)
    if len(questions)==0:
        return []
    r = search_similar_questions(query, questions, significance)
    r_sorted = sorted(r, key=lambda x: x[0], reverse=True)
    return [QuestionSearchOutputDTO(score=search_result[0], name=search_result[1].name, id=search_result[1].id) for search_result in r_sorted]
