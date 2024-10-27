import psycopg2
import json

db_config = {
    'dbname': 'liverpool_db',
    'user': 'postgres',
    'password': 'admin',
    'host': 'localhost',
    'port': 5432
}


def fetch_data():
    connection = psycopg2.connect(**db_config)
    cursor = connection.cursor()

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
    rows = cursor.fetchall()

    cursor.close()
    connection.close()

    return rows


def create_json(data):
    players = {}

    for row in data:
        player_id = row[0]

        if player_id not in players:
            players[player_id] = {
                "id": row[0],
                "ime": row[1],
                "prezime": row[2],
                "pozicija": row[3],
                "broj_dresa": row[4],
                "godine": row[5],
                "visina": row[6],
                "tezina": row[7],
                "drzava": row[8],
                "godina_pridruzivanja": row[9],
                "prosli_klubovi": []
            }

        if row[10]:
            players[player_id]["prosli_klubovi"].append({
                "klub": row[10],
                "godina_pocetka": row[11],
                "godina_zavrsetka": row[12]
            })

    return list(players.values())


def save_to_json(data, filename):
    with open(filename, 'w') as json_file:
        json.dump(data, json_file, indent=4)


def main():
    data = fetch_data()
    json_data = create_json(data)
    save_to_json(json_data, 'igraci_liverpoola.json')
    print("Done!")

main()
