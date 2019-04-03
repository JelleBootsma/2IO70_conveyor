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
class Bot2 extends Component {
  enum State {
    Running, ReceivingSequence, SequencePostProcessing
  };

  Bot2.State state;

  IBot2 iBot2;
  MQTTBot2 mQTTBot2;

  public Bot2(Locator locator) {this(locator, "");};

  public Bot2(Locator locator, String name) {this(locator, name, null);};

  public Bot2(Locator locator, String name, SystemComponent parent) {
    super(locator, name, parent);
    this.flushes = true;
    state = Bot2.State.Running;
    iBot2 = new IBot2();
    iBot2.in.name = "iBot2";
    iBot2.in.self = this;
    mQTTBot2 = new MQTTBot2();
    mQTTBot2.out.name = "mQTTBot2";
    mQTTBot2.out.self = this;

    iBot2.in.takeItemSig = () -> {Runtime.callIn(this, () -> {iBot2_takeItemSig();}, new Meta(this.iBot2, "takeItemSig"));};

    iBot2.in.assigned = () -> {Runtime.callIn(this, () -> {iBot2_assigned();}, new Meta(this.iBot2, "assigned"));};

    iBot2.in.reboot = () -> {Runtime.callIn(this, () -> {iBot2_reboot();}, new Meta(this.iBot2, "reboot"));};

    iBot2.in.startSequenceSig = () -> {Runtime.callIn(this, () -> {iBot2_startSequenceSig();}, new Meta(this.iBot2, "startSequenceSig"));};

    mQTTBot2.out.sequenceReceived = () -> {Runtime.callOut(this, () -> {mQTTBot2_sequenceReceived();}, new Meta(this.mQTTBot2, "sequenceReceived"));};

    mQTTBot2.out.available = () -> {Runtime.callOut(this, () -> {mQTTBot2_available();}, new Meta(this.mQTTBot2, "available"));};

    mQTTBot2.out.emergency = () -> {Runtime.callOut(this, () -> {mQTTBot2_emergency();}, new Meta(this.mQTTBot2, "emergency"));};

  }
  public void iBot2_takeItemSig() {
    mQTTBot2.in.takeItemSig.action();
  };

  public void iBot2_assigned() {
    if (state == Bot2.State.Running) { }
    else if (state == Bot2.State.ReceivingSequence) { }
    else if (state == Bot2.State.SequencePostProcessing) { }
    else this.runtime.illegal.action();
  };

  public void iBot2_reboot() {
    mQTTBot2.in.reboot.action();
    state = Bot2.State.Running;
  };

  public void iBot2_startSequenceSig() {
    if (state == Bot2.State.Running) {
      state = Bot2.State.ReceivingSequence;
      mQTTBot2.in.startSequenceSig.action();
    }
    else if (state == Bot2.State.ReceivingSequence) {
      this.runtime.illegal.action();
    }
    else if (state == Bot2.State.SequencePostProcessing) {
      this.runtime.illegal.action();
    }
    else this.runtime.illegal.action();
  };

  public void mQTTBot2_sequenceReceived() {
    if (state == Bot2.State.ReceivingSequence) {
      iBot2.out.sequenceReceived.action();
      state = Bot2.State.SequencePostProcessing;
    }
    else this.runtime.illegal.action();
  };

  public void mQTTBot2_available() {
    if (state == Bot2.State.SequencePostProcessing) {
      iBot2.out.available.action();
      state = Bot2.State.Running;
    }
    else this.runtime.illegal.action();
  };

  public void mQTTBot2_emergency() {
    iBot2.out.emergency.action();
  };

}
