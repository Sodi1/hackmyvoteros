FROM python:3.11.4-slim-bullseye as prod
RUN apt-get update && apt-get install -y \
  gcc \
  && rm -rf /var/lib/apt/lists/*

RUN pip install poetry==1.4.2

# Configuring poetry
RUN poetry config virtualenvs.create false

# Copying requirements of a project
COPY pyproject.toml poetry.lock /app/src/
WORKDIR /app/src

# Installing requirements
RUN poetry install --only main

RUN pip3 install transformers tqdm numpy scikit-learn scipy nltk sentencepiece
RUN pip3 install scikit-learn numpy
RUN pip3 install --no-deps sentence-transformers

# Removing gcc
RUN apt-get purge -y \
  gcc \
  && rm -rf /var/lib/apt/lists/*

# Copying actuall application
COPY . /app/src/
RUN poetry install --only main

CMD ["/usr/local/bin/python", "-m", "myvote_analytics"]

FROM prod as dev

RUN poetry install
