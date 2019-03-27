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
class Disk2 extends Component {

  IDisk2.State state;
  IDisk2.State reply_IDisk2_State;


  IDisk2 iDisk2;
  IDisk2Com iDisk2Com;

  public Disk2(Locator locator) {this(locator, "");};

  public Disk2(Locator locator, String name) {this(locator, name, null);};

  public Disk2(Locator locator, String name, SystemComponent parent) {
    super(locator, name, parent);
    this.flushes = true;
    state = IDisk2.State.NotAssigned;
    iDisk2 = new IDisk2();
    iDisk2.in.name = "iDisk2";
    iDisk2.in.self = this;
    iDisk2Com = new IDisk2Com();
    iDisk2Com.out.name = "iDisk2Com";
    iDisk2Com.out.self = this;

    iDisk2.in.assignedD = () -> {Runtime.callIn(this, () -> {iDisk2_assignedD();}, new Meta(this.iDisk2, "assignedD"));};

    iDisk2.in.assigned3 = () -> {Runtime.callIn(this, () -> {iDisk2_assigned3();}, new Meta(this.iDisk2, "assigned3"));};

    iDisk2.in.assigned4 = () -> {Runtime.callIn(this, () -> {iDisk2_assigned4();}, new Meta(this.iDisk2, "assigned4"));};

    iDisk2.in.unAssign = () -> {Runtime.callIn(this, () -> {iDisk2_unAssign();}, new Meta(this.iDisk2, "unAssign"));};

    iDisk2.in.reboot = () -> {Runtime.callIn(this, () -> {iDisk2_reboot();}, new Meta(this.iDisk2, "reboot"));};

    iDisk2.in.getState = () -> {return Runtime.callIn(this, () -> {return iDisk2_getState();}, new Meta(this.iDisk2, "getState"));};

    iDisk2Com.out.diskArrived = () -> {Runtime.callOut(this, () -> {iDisk2Com_diskArrived();}, new Meta(this.iDisk2Com, "diskArrived"));};

  }
  public void iDisk2_assignedD() {
    state = IDisk2.State.Assigned;
    iDisk2Com.in.assignedD.action();
  };

  public void iDisk2_assigned3() {
    state = IDisk2.State.Assigned;
    iDisk2Com.in.assigned3.action();
  };

  public void iDisk2_assigned4() {
    state = IDisk2.State.Assigned;
    iDisk2Com.in.assigned4.action();
  };

  public void iDisk2_unAssign() {
    state = IDisk2.State.NotAssigned;
    iDisk2Com.in.unAssign.action();
  };

  public void iDisk2_reboot() {
    state = IDisk2.State.NotAssigned;
    iDisk2Com.in.reboot.action();
  };

  public IDisk2.State iDisk2_getState() {
    reply_IDisk2_State = state;
    return reply_IDisk2_State;
  };

  public void iDisk2Com_diskArrived() {
    if (state == IDisk2.State.NotAssigned) {
      this.runtime.illegal.action();
    }
    else if (state == IDisk2.State.Assigned) {
      iDisk2.out.diskArrived.action();
    }
    else this.runtime.illegal.action();
  };

}
