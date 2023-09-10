import torch
import os
import numpy as np
from transformers import AutoModelForSequenceClassification
from transformers import BertTokenizerFast

tokenizer_path = os.path.abspath(
    "myvote_analytics/models/tokenizer-rubert-base-cased-sentiment-rurewiews")
tokenizer = BertTokenizerFast.from_pretrained(tokenizer_path)

model_path = os.path.abspath(
    "myvote_analytics/models/model-rubert-base-cased-sentiment-rurewiews")
model = AutoModelForSequenceClassification.from_pretrained(model_path, return_dict=True)

def detect_emotions(texts):
    return [detect_emotion(text) for text in texts]

@torch.no_grad()
def detect_emotion(text):
    inputs = tokenizer(text, max_length=512, padding=True, truncation=True, return_tensors='pt')
    outputs = model(**inputs)
    softmax = torch.nn.functional.softmax(outputs.logits, dim=1)
    probability_by_class = {
        'neutral':  float(softmax.numpy()[0][0]),
        'positive':  float(softmax.numpy()[0][1]),
        'negative':  float(softmax.numpy()[0][2]),
    }
    predicted = torch.argmax(softmax, dim=1).numpy()
    return [emotion_labels(predicted[0]), text, probability_by_class ]

def get_model():
    model_path = os.path.abspath("myvote_analytics/models/model-rubert-base-cased-sentiment-rurewiews")
    return AutoModelForSequenceClassification.from_pretrained(model_path, return_dict=True)

def emotion_labels(i):
    return ['neutral', 'positive', 'negative' ][i]

def get_tokenizer():
    tokenizer_path = os.path.abspath("myvote_analytics/models/tokenizer-rubert-base-cased-sentiment-rurewiews")
    return BertTokenizerFast.from_pretrained(tokenizer_path)
