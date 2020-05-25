from IoT import *
from handlers.utils import get_device_data, change_settings, test_connection


class SuperCoffeeMakerHandler:
    def __init__(self, name, communicator):
        self.name = name
        self._proxy = None
        self.communicator = communicator
        self.device_type = "coffee makers"
        self.allowed_actions = {"getDeviceData",
                                "changeSettings",
                                "makeCoffee",
                                "makeCustomCoffeeOrder"}

        self.settings = ["temperature", "volume"]

        self.coffee_types = ["ESPRESSO",
                             "AMERICANO",
                             "CAPPUCCINO",
                             "LATTE",
                             "COLDWATER",
                             "COLDMILK",
                             "COLDCOFFEE",
                             "HOTWATER",
                             "HOTMILK"]

        self.coffee_types_map = {
            "ESPRESSO": CoffeeType.ESPRESSO,
            "AMERICANO": CoffeeType.AMERICANO,
            "CAPPUCCINO": CoffeeType.CAPPUCCINO,
            "LATTE": CoffeeType.LATTE,
            "COLDWATER": CoffeeType.COLDWATER,
            "COLDMILK": CoffeeType.COLDMILK,
            "COLDCOFFEE": CoffeeType.COLDCOFFEE,
            "HOTWATER": CoffeeType.HOTWATER,
            "HOTMILK": CoffeeType.HOTMILK}

    @property
    def proxy(self):
        if not self._proxy:
            proxy = self.communicator.propertyToProxy(self.name)
            self._proxy = ISuperCoffeeMakerPrx.checkedCast(proxy)

        return self._proxy

    def print_allowed_actions(self):
        print()
        print("Allowed actions:")
        print("- getDeviceData")
        print("- changeSettings")
        print("- makeCoffee")
        print("- makeCustomCoffeeOrder", end="")

    def handle_action(self, action):
        if action == "getDeviceData":
            get_device_data(self)
        elif action == "changeSettings":
            change_settings(self)
        elif action == "makeCoffee":
            print("Enter coffee type, one of:", self.coffee_types,
                  "(can be in lowercase).")
            coffee_type = input("=======> ")

            try:
                coffee_type = self.coffee_types_map[coffee_type.upper()]
            except KeyError:
                print("Error: provided coffee type was not recognized")
                return

            try:
                test_connection(self)
                coffee = self.proxy.makeCoffee(coffee_type)
            except UnsupportedCoffeeTypeException as e:
                print("Error:", e.reason, file=sys.stderr)
                print()
                return
            except Ice.ObjectNotExistException:
                print("Error: servant object appropriate for this action was "
                      "not found on the server.")
                return

            print("Coffee ready: ", coffee.coffeeType.name.lower(), ", ",
                  "temperature: ", coffee.temperature, " deg. C, ",
                  "volume: ", coffee.volume, " ml", sep="")

        elif action == "makeCustomCoffeeOrder":
            print("Enter coffee orders.")
            print("Available coffee types:", self.coffee_types,
                  "(can be in lowercase).")

            order = []
            while True:
                coffee_type = input("=======> Coffee type: ")
                if not coffee_type:
                    break

                temperature = input("=======> Temperature: ")
                if not temperature:
                    break
                temperature = int(temperature)

                volume = input("=======> Volume: ")
                if not volume:
                    break
                volume = int(volume)

                print()
                try:
                    coffee_type = self.coffee_types_map[coffee_type.upper()]
                except KeyError:
                    print("Error: provided coffee type was not recognized")
                    continue

                order.append(
                    SingleCoffeeOrder(coffee_type, temperature, volume))

            try:
                test_connection(self)

                # cut off last element because of weird ICE generated code
                # it adds default SingleCoffeeOrder for no reason
                result = self.proxy.makeCustomCoffeeOrder(order)[:-1]
            except ValueOutOfRangeException as e:
                print("Error:", e)
                return
            except Ice.ObjectNotExistException:
                print("Error: servant object appropriate for this action was "
                      "not found on the server.")
                return

            print("Order ready:")
            for coffee in result:
                print(coffee.coffeeType.name.lower(), ", ",
                      "temperature: ", coffee.temperature, " deg. C, ",
                      "volume: ", coffee.volume, " ml", sep="")
            print()
