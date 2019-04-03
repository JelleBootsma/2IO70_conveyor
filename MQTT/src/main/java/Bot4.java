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
class Bot4 extends Component {

  IBot4.State state;
  IBot4.State reply_IBot4_State;


  IBot4 iBot4;
  MQTTBot4 mQTTBot4;

  public Bot4(Locator locator) {this(locator, "");};

  public Bot4(Locator locator, String name) {this(locator, name, null);};

  public Bot4(Locator locator, String name, SystemComponent parent) {
    super(locator, name, parent);
    this.flushes = true;
    state = IBot4.State.Free;
    iBot4 = new IBot4();
    iBot4.in.name = "iBot4";
    iBot4.in.self = this;
    mQTTBot4 = new MQTTBot4();
    mQTTBot4.out.name = "mQTTBot4";
    mQTTBot4.out.self = this;

    iBot4.in.assigned = () -> {Runtime.callIn(this, () -> {iBot4_assigned();}, new Meta(this.iBot4, "assigned"));};

    iBot4.in.takeItemSig = () -> {Runtime.callIn(this, () -> {iBot4_takeItemSig();}, new Meta(this.iBot4, "takeItemSig"));};

    iBot4.in.placeItemGrantedSig = () -> {Runtime.callIn(this, () -> {iBot4_placeItemGrantedSig();}, new Meta(this.iBot4, "placeItemGrantedSig"));};

    iBot4.in.placeItemDeniedSig = () -> {Runtime.callIn(this, () -> {iBot4_placeItemDeniedSig();}, new Meta(this.iBot4, "placeItemDeniedSig"));};

    iBot4.in.reboot = () -> {Runtime.callIn(this, () -> {iBot4_reboot();}, new Meta(this.iBot4, "reboot"));};

    iBot4.in.getState = () -> {return Runtime.callIn(this, () -> {return iBot4_getState();}, new Meta(this.iBot4, "getState"));};

    mQTTBot4.out.availableSig = () -> {Runtime.callOut(this, () -> {mQTTBot4_availableSig();}, new Meta(this.mQTTBot4, "availableSig"));};

    mQTTBot4.out.placeItemSig = () -> {Runtime.callOut(this, () -> {mQTTBot4_placeItemSig();}, new Meta(this.mQTTBot4, "placeItemSig"));};

    mQTTBot4.out.placedItemSig = () -> {Runtime.callOut(this, () -> {mQTTBot4_placedItemSig();}, new Meta(this.mQTTBot4, "placedItemSig"));};

    mQTTBot4.out.emergency = () -> {Runtime.callOut(this, () -> {mQTTBot4_emergency();}, new Meta(this.mQTTBot4, "emergency"));};

  }
  public void iBot4_assigned() {
    if (state == IBot4.State.Free) {
      state = IBot4.State.WaitingForItem;
    }
    else if (state == IBot4.State.WaitingForItem) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.Busy) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.WaitingForGrant) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.placingBack) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.PostProcessing) {
      this.runtime.illegal.action();
    }
    else this.runtime.illegal.action();
  };

  public void iBot4_takeItemSig() {
    if (state == IBot4.State.Free) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.WaitingForItem) {
      mQTTBot4.in.takeItemSig.action();
      state = IBot4.State.Busy;
    }
    else if (state == IBot4.State.Busy) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.WaitingForGrant) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.placingBack) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.PostProcessing) {
      this.runtime.illegal.action();
    }
    else this.runtime.illegal.action();
  };

  public void iBot4_placeItemGrantedSig() {
    if (state == IBot4.State.Free) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.WaitingForItem) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.Busy) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.WaitingForGrant) {
      mQTTBot4.in.placeItemGrantedSig.action();
      state = IBot4.State.placingBack;
    }
    else if (state == IBot4.State.placingBack) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.PostProcessing) {
      this.runtime.illegal.action();
    }
    else this.runtime.illegal.action();
  };

  public void iBot4_placeItemDeniedSig() {
    if (state == IBot4.State.Free) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.WaitingForItem) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.Busy) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.WaitingForGrant) {
      mQTTBot4.in.placeItemDeniedSig.action();
      state = IBot4.State.Busy;
    }
    else if (state == IBot4.State.placingBack) {
      this.runtime.illegal.action();
    }
    else if (state == IBot4.State.PostProcessing) {
      this.runtime.illegal.action();
    }
    else this.runtime.illegal.action();
  };

  public void iBot4_reboot() {
    mQTTBot4.in.reboot.action();
    state = IBot4.State.Free;
  };

  public IBot4.State iBot4_getState() {
    reply_IBot4_State = state;
    return reply_IBot4_State;
  };

  public void mQTTBot4_availableSig() {
    if (state == IBot4.State.Busy) {
      state = IBot4.State.Free;
      iBot4.out.availableSig.action();
    }
    else if (state == IBot4.State.PostProcessing) {
      state = IBot4.State.Free;
      iBot4.out.availableSig.action();
    }
    else this.runtime.illegal.action();
  };

  public void mQTTBot4_placeItemSig() {
    if (state == IBot4.State.Busy) {
      state = IBot4.State.WaitingForGrant;
      iBot4.out.placeItemSig.action();
    }
    else this.runtime.illegal.action();
  };

  public void mQTTBot4_placedItemSig() {
    if (state == IBot4.State.placingBack) {
      iBot4.out.placedItemSig.action();
      state = IBot4.State.PostProcessing;
    }
    else this.runtime.illegal.action();
  };

  public void mQTTBot4_emergency() {
    iBot4.out.emergency.action();
  };

}
