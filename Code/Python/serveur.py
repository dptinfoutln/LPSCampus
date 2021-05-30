from flask import Flask, Request, request
from flask_restful import Resource, Api

import json

import model as M
from time import sleep

app = Flask(__name__)
api = Api(app)


class Position(Resource):
    def post(self):
        M.init()
    
    def post(self):
        M.init()
        res = ""
        for s in predictions[1]:
            res += s[0] + " : " + str(s[1]) + ","
  
        print("res:", res)
        
        return res

api.add_resource(Position, '/position')

if __name__ == "__main__":
    success = False
    while not success:
        try :
            M.init()
            success = True
        except Exception:
            sleep(60)
        
    app.run(host='0.0.0.0', port=5000, debug=True)
