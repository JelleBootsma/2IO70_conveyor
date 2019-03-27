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
class Disk4 extends Component {

  IDisk4.State state;
  IDisk4.State reply_IDisk4_State;


  IDisk4 iDisk4;
  IDisk4Com iDisk4Com;

  public Disk4(Locator locator) {this(locator, "");};

  public Disk4(Locator locator, String name) {this(locator, name, null);};

  public Disk4(Locator locator, String name, SystemComponent parent) {
    super(locator, name, parent);
    this.flushes = true;
    state = IDisk4.State.NotAssigned;
    iDisk4 = new IDisk4();
    iDisk4.in.name = "iDisk4";
    iDisk4.in.self = this;
    iDisk4Com = new IDisk4Com();
    iDisk4Com.out.name = "iDisk4Com";
    iDisk4Com.out.self = this;

    iDisk4.in.assignedD = () -> {Runtime.callIn(this, () -> {iDisk4_assignedD();}, new Meta(this.iDisk4, "assignedD"));};

    iDisk4.in.assigned3 = () -> {Runtime.callIn(this, () -> {iDisk4_assigned3();}, new Meta(this.iDisk4, "assigned3"));};

    iDisk4.in.assigned4 = () -> {Runtime.callIn(this, () -> {iDisk4_assigned4();}, new Meta(this.iDisk4, "assigned4"));};

    iDisk4.in.unAssign = () -> {Runtime.callIn(this, () -> {iDisk4_unAssign();}, new Meta(this.iDisk4, "unAssign"));};

    iDisk4.in.reboot = () -> {Runtime.callIn(this, () -> {iDisk4_reboot();}, new Meta(this.iDisk4, "reboot"));};

    iDisk4.in.getState = () -> {return Runtime.callIn(this, () -> {return iDisk4_getState();}, new Meta(this.iDisk4, "getState"));};

    iDisk4Com.out.diskArrived = () -> {Runtime.callOut(this, () -> {iDisk4Com_diskArrived();}, new Meta(this.iDisk4Com, "diskArrived"));};

  }
  public void iDisk4_assignedD() {
    state = IDisk4.State.WaitForArrival;
    iDisk4Com.in.assignedD.action();
  };

  public void iDisk4_assigned3() {
    state = IDisk4.State.WaitForArrival;
    iDisk4Com.in.assigned3.action();
  };

  public void iDisk4_assigned4() {
    state = IDisk4.State.WaitForArrival;
    iDisk4Com.in.assigned4.action();
  };

  public void iDisk4_unAssign() {
    state = IDisk4.State.NotAssigned;
    iDisk4Com.in.unAssign.action();
  };

  public void iDisk4_reboot() {
    state = IDisk4.State.NotAssigned;
    iDisk4Com.in.reboot.action();
  };

  public IDisk4.State iDisk4_getState() {
    reply_IDisk4_State = state;
    return reply_IDisk4_State;
  };

  public void iDisk4Com_diskArrived() {
    if (state == IDisk4.State.WaitForArrival) {
      iDisk4.out.diskArrived.action();
      state = IDisk4.State.Arrived;
    }
    else this.runtime.illegal.action();
  };

}
