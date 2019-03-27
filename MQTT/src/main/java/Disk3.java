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
class Disk3 extends Component {

  IDisk3.State state;
  IDisk3.State reply_IDisk3_State;


  IDisk3 iDisk3;
  IDisk3Com iDisk3Com;

  public Disk3(Locator locator) {this(locator, "");};

  public Disk3(Locator locator, String name) {this(locator, name, null);};

  public Disk3(Locator locator, String name, SystemComponent parent) {
    super(locator, name, parent);
    this.flushes = true;
    state = IDisk3.State.NotAssigned;
    iDisk3 = new IDisk3();
    iDisk3.in.name = "iDisk3";
    iDisk3.in.self = this;
    iDisk3Com = new IDisk3Com();
    iDisk3Com.out.name = "iDisk3Com";
    iDisk3Com.out.self = this;

    iDisk3.in.assignedD = () -> {Runtime.callIn(this, () -> {iDisk3_assignedD();}, new Meta(this.iDisk3, "assignedD"));};

    iDisk3.in.assigned3 = () -> {Runtime.callIn(this, () -> {iDisk3_assigned3();}, new Meta(this.iDisk3, "assigned3"));};

    iDisk3.in.assigned4 = () -> {Runtime.callIn(this, () -> {iDisk3_assigned4();}, new Meta(this.iDisk3, "assigned4"));};

    iDisk3.in.unAssign = () -> {Runtime.callIn(this, () -> {iDisk3_unAssign();}, new Meta(this.iDisk3, "unAssign"));};

    iDisk3.in.reboot = () -> {Runtime.callIn(this, () -> {iDisk3_reboot();}, new Meta(this.iDisk3, "reboot"));};

    iDisk3.in.getState = () -> {return Runtime.callIn(this, () -> {return iDisk3_getState();}, new Meta(this.iDisk3, "getState"));};

    iDisk3Com.out.diskArrived = () -> {Runtime.callOut(this, () -> {iDisk3Com_diskArrived();}, new Meta(this.iDisk3Com, "diskArrived"));};

  }
  public void iDisk3_assignedD() {
    state = IDisk3.State.WaitForArrival;
    iDisk3Com.in.assignedD.action();
  };

  public void iDisk3_assigned3() {
    state = IDisk3.State.WaitForArrival;
    iDisk3Com.in.assigned3.action();
  };

  public void iDisk3_assigned4() {
    state = IDisk3.State.WaitForArrival;
    iDisk3Com.in.assigned4.action();
  };

  public void iDisk3_unAssign() {
    state = IDisk3.State.NotAssigned;
    iDisk3Com.in.unAssign.action();
  };

  public void iDisk3_reboot() {
    state = IDisk3.State.NotAssigned;
    iDisk3Com.in.reboot.action();
  };

  public IDisk3.State iDisk3_getState() {
    reply_IDisk3_State = state;
    return reply_IDisk3_State;
  };

  public void iDisk3Com_diskArrived() {
    if (state == IDisk3.State.WaitForArrival) {
      iDisk3.out.diskArrived.action();
      state = IDisk3.State.Arrived;
    }
    else this.runtime.illegal.action();
  };

}
