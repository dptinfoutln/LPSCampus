# server.py

from flask import Flask, jsonify, request

app = Flask(__name__)

# Ex de commande cuRL pour le POST :
#    en local :  curl --header "Content-Type: application/json" --request POST --data '[{"IP":"192.5.6.8","signal":"0.563"},{"IP":"188.1.9.2","signal":"0.124"}]'   http://localhost:5000/data
#    en Cloud :  curl --header "Content-Type: application/json" --request POST --data '[{"IP":"192.5.6.8","signal":"0.563"},{"IP":"188.1.9.2","signal":"0.124"}]'   http://35.237.226.74/data

# Exemple de Json, juste pour les tests
tasks = [
    {
        'id': 1,
        'title': u'information numero 1',
        'description': u'Ceci est un premier message pour test',
        'done': False
    },
    {
        'id': 2,
        'title': u'information numero 2',
        'description': u'..et ceci est un second message de test',
        'done': False
    }
]

# Traitement GET sur le root /
@app.route('/accueil', methods=['GET'])
def get_menu():
    return "<!doctype html> \
            <html lang='en' class='no-js'> \
                <head> \
                    <title>Message de bienvenue</title> \
                </head> \
                <body> \
                    </br></br></br></br> \
	                <h1 style='text-align: center'>Bienvenue sur le serveur REST !!</h1> \
                </body> \
            </html>"

# Traitement POST sur la propriÃ©tÃ© data
@app.route("/data", methods=["POST"])
def post_data():
    # Recupere les donnees client
    input_json = request.get_json(force=True) 
    print ("donnees client: ", input_json)

    # Filtre les donnÃ©es pour le module machine learning
    donnees_filtree = filtre_json(input_json)

    # Appelle le module de machine learning
    resultat_ml = machine_learning(donnees_filtree)
      # print ("resultats: ", resultat_ml)

    # formate la requete de retour pour le client (ici un test)
    output_json = {'requete': input_json}, {'reponse': 42} # ex: {'reponse': resultat_ml}
    return jsonify(output_json)


# Traitement GET (test)
@app.route('/test', methods=['GET'])
def get_tasks():
    return jsonify({'tasks': tasks})


# Extraction donnees pertinantes pour le module machine learning
def filtre_json(input_json):
    return input_json


# Module de machine learning
def machine_learning(donnees_filtree):
    return donnees_filtree


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port='5000')