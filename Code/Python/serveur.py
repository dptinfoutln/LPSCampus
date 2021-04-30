from flask import Flask, Request, request
from flask_restful import Resource, Api

import json

import model as M

app = Flask(__name__)
api = Api(app)

# name = {"nom1" : {"age" : 19, "lieu" : "Toulon"}
#         "nom2" : {"age" : 23, "lieu" : "Hyeres"}
#         }

class Position(Resource):
    def post(self):
        content = request.get_json(silent=True)
        
        res = M.get_predict(content)
        
        return res[0]

api.add_resource(Position, '/position')

if __name__ == "__main__":
    M.init()
    app.run(host='0.0.0.0', port=5000, debug=True)
