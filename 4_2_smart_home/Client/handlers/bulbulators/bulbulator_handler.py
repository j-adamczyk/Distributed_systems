import Ice
from IoT import *
from ..utils import get_device_data, change_settings, test_connection


class BulbulatorInfo:
    def __init__(self, name, communicator):
        self.name = name
        self.communicator = communicator
        self._proxy = None
        self.device_type = "bulbulators"
        self.allowed_actions = {"getDeviceData",
                                "changeSettings",
                                "bulbulbul"}

        self.settings = ["bulRepeatNumber"]

    @property
    def proxy(self):
        if not self._proxy:
            proxy = self.communicator.propertyToProxy(self.name)
            self._proxy = IBulbulatorPrx.checkedCast(proxy)

        return self._proxy

    def print_allowed_actions(self):
        print()
        print("Allowed actions:")
        print("- getDeviceData")
        print("- changeSettings")
        print("- bulbulbul", end="")

    def handle_action(self, action):
        if action == "getDeviceData":
            get_device_data(self)
        elif action == "changeSettings":
            change_settings(self)
        elif action == "bulbulbul":
            test_connection(self)
            try:
                print(self.proxy.bulbulbul())
                print()
            except Ice.ObjectNotExistException:
                print("Error: servant object appropriate for this action was "
                      "not found on the server.")

