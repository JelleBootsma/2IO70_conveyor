// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
class Belt extends SystemComponent {
  BeltController beltController;
  Bot2 bot2;
  Bot3 bot3;
  Bot4 bot4;
  Disk2 disk2;
  Disk3 disk3;
  Disk4 disk4;
  IBeltController iBeltcontroller;
  MQTTBot2 mQTTBot2;
  MQTTBot3 mQTTBot3;
  MQTTBot4 mQTTBot4;
  IDisk2Com iDisk2Com;
  IDisk3Com iDisk3Com;
  IDisk4Com iDisk4Com;
  IBeltEngines iBeltEngines;
  IGate iGate;


  public Belt(Locator locator) {this(locator, "");};

  public Belt(Locator locator, String name) {this(locator, name, null);};

  public Belt(Locator locator, String name, SystemComponent parent) {
    super(locator, name, parent);
    beltController = new BeltController(locator, "beltController", this);
    bot2 = new Bot2(locator, "bot2", this);
    bot3 = new Bot3(locator, "bot3", this);
    bot4 = new Bot4(locator, "bot4", this);
    disk2 = new Disk2(locator, "disk2", this);
    disk3 = new Disk3(locator, "disk3", this);
    disk4 = new Disk4(locator, "disk4", this);
    iBeltcontroller = beltController.iBeltController;
    iBeltEngines = beltController.iBeltEngines;
    iDisk2Com = disk2.iDisk2Com;
    iDisk3Com = disk3.iDisk3Com;
    iDisk4Com = disk4.iDisk4Com;
    iGate = beltController.iGate;
    mQTTBot2 = bot2.mQTTBot2;
    mQTTBot3 = bot3.mQTTBot3;
    mQTTBot4 = bot4.mQTTBot4;

    Interface.connect(bot2.iBot2, beltController.iBot2);
    Interface.connect(bot3.iBot3, beltController.iBot3);
    Interface.connect(bot4.iBot4, beltController.iBot4);
    Interface.connect(disk2.iDisk2, beltController.iDisk2);
    Interface.connect(disk3.iDisk3, beltController.iDisk3);
    Interface.connect(disk4.iDisk4, beltController.iDisk4);
  };
}
