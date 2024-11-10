from flask import Flask, jsonify, request, Response
from flask_cors import CORS, cross_origin
import psycopg2
import csv
from io import StringIO

app = Flask(__name__)
cors = CORS(app)
app.config['CORS_HEADERS'] = 'Content-Type'


db_config = {
    'dbname': 'liverpool_db',
    'user': 'postgres',
    'password': 'admin',
    'host': 'localhost',
    'port': 5432
}

def fetch_data(search='', attribute='default'):
    connection = psycopg2.connect(**db_config)
    cursor = connection.cursor()
    if attribute == 'default':
        query = """
        SELECT 
            i.id,
            i.ime,
            i.prezime,
            i.pozicija,
            i.broj_dresa,
            i.godine,
            i.visina,
            i.tezina,
            i.drzava,
            i.godina_pridruzivanja,
            pk.klub,
            pk.godina_pocetka,
            pk.godina_zavrsetka
        FROM 
            igraci i
        LEFT JOIN 
            prosli_klubovi pk ON i.id = pk.igrac_id;
        """
        cursor.execute(query)
    elif attribute == 'sve':
        query = """
        SELECT 
            i.id,
            i.ime,
            i.prezime,
            i.pozicija,
            i.broj_dresa,
            i.godine,
            i.visina,
            i.tezina,
            i.drzava,
            i.godina_pridruzivanja,
            pk.klub,
            pk.godina_pocetka,
            pk.godina_zavrsetka
        FROM 
            igraci i
        LEFT JOIN 
            prosli_klubovi pk ON i.id = pk.igrac_id
        WHERE
            LOWER(i.ime) LIKE LOWER(%s)
            OR LOWER(i.prezime) LIKE LOWER(%s)
            OR LOWER(i.pozicija) LIKE LOWER(%s)
            OR LOWER(i.broj_dresa::TEXT) LIKE LOWER(%s)
            OR LOWER(i.godine::TEXT) LIKE LOWER(%s)
            OR LOWER(i.visina::TEXT) LIKE LOWER(%s)
            OR LOWER(i.tezina::TEXT) LIKE LOWER(%s)
            OR LOWER(i.drzava) LIKE LOWER(%s)
            OR LOWER(i.godina_pridruzivanja::TEXT) LIKE LOWER(%s)
            OR LOWER(pk.klub) LIKE LOWER(%s)
            OR LOWER(pk.godina_pocetka::TEXT) LIKE LOWER(%s)
            OR LOWER(pk.godina_zavrsetka::TEXT) LIKE LOWER(%s);
        """
        search_term = f"%{search}%"
        cursor.execute(query, tuple([search_term] * 12))
    else:
        query = f"""
        SELECT 
            i.id,
            i.ime,
            i.prezime,
            i.pozicija,
            i.broj_dresa,
            i.godine,
            i.visina,
            i.tezina,
            i.drzava,
            i.godina_pridruzivanja,
            pk.klub,
            pk.godina_pocetka,
            pk.godina_zavrsetka
        FROM 
            igraci i
        LEFT JOIN 
            prosli_klubovi pk ON i.id = pk.igrac_id
        WHERE 
            LOWER(i.{attribute}) LIKE LOWER(%s);
        """
        cursor.execute(query, (f"%{search}%",))

    rows = cursor.fetchall()
    cursor.close()
    connection.close()

    return rows



@app.route('/api/igraci', methods=['GET'])
@cross_origin()
def get_igraci():
    search = request.args.get('search', '')
    attribute = request.args.get('attribute', 'default')
    data = fetch_data(search, attribute)

    players = []
    for row in data:
        players.append({
            'id': row[0],
            'ime': row[1],
            'prezime': row[2],
            'pozicija': row[3],
            'broj_dresa': row[4],
            'godine': row[5],
            'visina': row[6],
            'tezina': row[7],
            'drzava': row[8],
            'godina_pridruzivanja': row[9],
            'prosli_klubovi': [{'klub': row[10], 'godina_pocetka': row[11], 'godina_zavrsetka': row[12]}] if row[10] else []
        })

    return jsonify(players)

@app.route('/api/igraci/json', methods=['GET'])
@cross_origin()
def get_igraci_json():
    search = request.args.get('search', '')
    attribute = request.args.get('attribute', 'default')
    data = fetch_data(search, attribute)

    players = []
    for row in data:
        players.append({
            'id': row[0],
            'ime': row[1],
            'prezime': row[2],
            'pozicija': row[3],
            'broj_dresa': row[4],
            'godine': row[5],
            'visina': row[6],
            'tezina': row[7],
            'drzava': row[8],
            'godina_pridruzivanja': row[9],
            'prosli_klubovi': [{'klub': row[10], 'godina_pocetka': row[11], 'godina_zavrsetka': row[12]}] if row[10] else []
        })

    return Response(jsonify(players),
         mimetype='application/json',
         headers={'Content-Disposition':'attachment;filename=igraci_liverpool.json'})

@app.route('/api/igraci/csv', methods=['GET'])
@cross_origin()
def get_igraci_csv():
    search = request.args.get('search', '')
    attribute = request.args.get('attribute', 'default')
    data = fetch_data(search, attribute)

    output = StringIO()
    writer = csv.writer(output)

    writer.writerow([
        'id', 'ime', 'prezime', 'pozicija', 'broj_dresa', 'godine', 'visina', 'tezina',
        'drzava', 'godina_pridruzivanja', 'klub', 'godina_pocetka', 'godina_zavrsetka'
    ])

    for row in data:
        writer.writerow([
            row[0],
            row[1],
            row[2],
            row[3],
            row[4],
            row[5],
            row[6],
            row[7],
            row[8],
            row[9],
            row[10] if row[10] else '',
            row[11] if row[10] else '',
            row[12] if row[10] else '',
        ])

    output.seek(0)

    return Response(output.getvalue(),
                    mimetype='text/csv',
                    headers={'Content-Disposition': 'attachment; filename=igraci_liverpool.csv'})

if __name__ == '__main__':
    app.run(debug=True)
