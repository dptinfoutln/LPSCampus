FROM python:3.8-slim

COPY Code/Python/requirements.txt .

RUN pip install -r requirements.txt

COPY Code/Python /home/app/src

EXPOSE 5000

CMD ["python", "/home/app/src/serveur.py"]
