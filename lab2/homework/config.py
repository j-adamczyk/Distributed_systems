import googlemaps


class Config:
    def __init__(self):
        with open("keys.txt") as file:
            for line in file:
                api_type, key = line.split()
                api_type = api_type.lower()
                if api_type == "google":
                    self.google_key = key
                    self.google_client = googlemaps.Client(key=key)
                elif api_type == "dark_sky":
                    self.dark_sky_key = key
