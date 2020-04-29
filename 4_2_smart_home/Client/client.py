import Ice
from IoT import *
from handlers.bulbulators.bulbulator_handler import BulbulatorInfo
from handlers.coffee_makers.basic_handler import BasicCoffeeMakerHandler
from handlers.coffee_makers.cold_handler import ColdCoffeeMakerHandler
from handlers.coffee_makers.super_handler import SuperCoffeeMakerHandler
from handlers.microwaves.basic_handler import BasicMicrowaveHandler
from handlers.microwaves.super_handler import SuperMicrowaveHandler


config_file = "config.client"


def get_proxies(communicator):
    devices = dict()
    with open(config_file) as file:
        for line in file:
            if line == "\n":
                continue
            if line.startswith("# END DEVICE DEFINITIONS"):
                break

            name = line.split("=", 1)[0]

            if "bulbulator" in name:
                device = BulbulatorInfo(name, communicator)
            elif "basicCoffeeMaker" in name:
                device = BasicCoffeeMakerHandler(name, communicator)
            elif "coldCoffeeMaker" in name:
                device = ColdCoffeeMakerHandler(name, communicator)
            elif "superCoffeeMaker" in name:
                device = SuperCoffeeMakerHandler(name, communicator)
            elif "basicMicrowave" in name:
                device = BasicMicrowaveHandler(name, communicator)
            elif "superMicrowave" in name:
                device = SuperMicrowaveHandler(name, communicator)
            else:
                raise ValueError("Device", name, " not recognized.")

            devices[name] = device
    return devices


if __name__ == "__main__":
    with Ice.initialize(config_file) as communicator:
        # don't create proxies yet - they'll be created lazily with first
        # communication attempt, this way server can lazily create servants
        devices = get_proxies(communicator)
        if not devices:
            print("No proxies found for provided file.")
            exit(1)

        print("Client ready, entering processing loop.")

        print("Available devices:")
        for device_name in devices:
            print(device_name)

        print()
        print("To use device, enter it's name first. Empty input exits to "
              "main menu (unless written otherwise).")
        print()

        while True:
            print("Select device:", end="")
            name = input("\n=> ")
            if not name or name == "exit":
                print("Goodbye!")
                exit(0)
            if name not in devices:
                print("Unknown device.")
                print()
                continue

            device = devices[name]
            device.print_allowed_actions()

            action = input("\n====> ")
            if not action:
                continue
            if action not in device.allowed_actions:
                print("Illegal action for ", device.name, ".", sep="")
                print()
                continue

            try:
                device.handle_action(action)
            except Ice.EndpointParseException:
                print("Incorrect port for device ", device.name,
                      ", removing it from available devices.", sep="")
                del devices[name]
                print()
                print("Available devices:")
                for device_name in devices:
                    print(device_name)
                print()
