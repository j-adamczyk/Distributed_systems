from IoT import *
from handlers.utils import get_device_data, change_settings, test_connection


class SuperMicrowaveHandler:
    def __init__(self, name, communicator):
        self.name = name
        self._proxy = None
        self.communicator = communicator
        self.device_type = "microwaves"
        self.allowed_actions = {"getDeviceData",
                                "changeSettings",
                                "microwave",
                                "toggleUsingSound"}

        self.settings = ["power"]

        self.microwave_modes = ["HEATUP",
                                "KEEPWARM",
                                "DEFROST",
                                "GRILL"]

        self.microwaves_modes_map = {
            "HEATUP": MicrowaveMode.HEATUP,
            "KEEPWARM": MicrowaveMode.KEEPWARM,
            "DEFROST": MicrowaveMode.DEFROST,
            "GRILL": MicrowaveMode.GRILL
        }

    @property
    def proxy(self):
        if not self._proxy:
            proxy = self.communicator.propertyToProxy(self.name)
            self._proxy = IMicrowavePrx.checkedCast(proxy)

        return self._proxy

    def print_allowed_actions(self):
        print()
        print("Allowed actions:")
        print("- getDeviceData")
        print("- changeSettings")
        print("- microwave")
        print("- toggleUsingSound", end="")

    def handle_action(self, action):
        if action == "getDeviceData":
            get_device_data(self)
        elif action == "changeSettings":
            change_settings(self)
        elif action == "microwave":
            print("Enter microwave mode, one of:", self.microwave_modes,
                  "(can be in lowercase).")
            microwave_mode = input("=======> ")

            try:
                microwave_mode = self.microwaves_modes_map[
                    microwave_mode.upper()]
            except KeyError:
                print("Error: provided microwave mode was not recognized")
                return

            try:
                test_connection(self)
                print(self.proxy.microwave(microwave_mode))
                print()
            except UnsupportedCoffeeTypeException as e:
                print("Error:", e.reason)
                print()
            except Ice.ObjectNotExistException:
                print("Error: servant object appropriate for this action was "
                      "not found on the server.")

        elif action == "toggleUsingSound":
            test_connection(self)
            try:
                self.proxy.toggleUsingSound()
                print("Using sound toggled")
                print()
            except Ice.ObjectNotExistException:
                print("Error: servant object appropriate for this action was "
                      "not found on the server.")
                return
