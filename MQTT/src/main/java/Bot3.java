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
class Bot3 extends Component {

  IBot3.State state;
  IBot3.State reply_IBot3_State;


  IBot3 iBot3;
  MQTTBot3 mQTTBot3;

  public Bot3(Locator locator) {this(locator, "");};

  public Bot3(Locator locator, String name) {this(locator, name, null);};

  public Bot3(Locator locator, String name, SystemComponent parent) {
    super(locator, name, parent);
    this.flushes = true;
    state = IBot3.State.Free;
    iBot3 = new IBot3();
    iBot3.in.name = "iBot3";
    iBot3.in.self = this;
    mQTTBot3 = new MQTTBot3();
    mQTTBot3.out.name = "mQTTBot3";
    mQTTBot3.out.self = this;

    iBot3.in.assigned = () -> {Runtime.callIn(this, () -> {iBot3_assigned();}, new Meta(this.iBot3, "assigned"));};

    iBot3.in.takeItemSig = () -> {Runtime.callIn(this, () -> {iBot3_takeItemSig();}, new Meta(this.iBot3, "takeItemSig"));};

    iBot3.in.placeItemGrantedSig = () -> {Runtime.callIn(this, () -> {iBot3_placeItemGrantedSig();}, new Meta(this.iBot3, "placeItemGrantedSig"));};

    iBot3.in.placeItemDeniedSig = () -> {Runtime.callIn(this, () -> {iBot3_placeItemDeniedSig();}, new Meta(this.iBot3, "placeItemDeniedSig"));};

    iBot3.in.reboot = () -> {Runtime.callIn(this, () -> {iBot3_reboot();}, new Meta(this.iBot3, "reboot"));};

    iBot3.in.getState = () -> {return Runtime.callIn(this, () -> {return iBot3_getState();}, new Meta(this.iBot3, "getState"));};

    mQTTBot3.out.availableSig = () -> {Runtime.callOut(this, () -> {mQTTBot3_availableSig();}, new Meta(this.mQTTBot3, "availableSig"));};

    mQTTBot3.out.placeItemSig = () -> {Runtime.callOut(this, () -> {mQTTBot3_placeItemSig();}, new Meta(this.mQTTBot3, "placeItemSig"));};

    mQTTBot3.out.placedItemSig = () -> {Runtime.callOut(this, () -> {mQTTBot3_placedItemSig();}, new Meta(this.mQTTBot3, "placedItemSig"));};

  }
  public void iBot3_assigned() {
    if (state == IBot3.State.Free) {
      state = IBot3.State.WaitingForItem;
    }
    else if (state == IBot3.State.WaitingForItem) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.Busy) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.WaitingForGrant) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.placingBack) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.PostProcessing) {
      this.runtime.illegal.action();
    }
    else this.runtime.illegal.action();
  };

  public void iBot3_takeItemSig() {
    if (state == IBot3.State.Free) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.WaitingForItem) {
      mQTTBot3.in.takeItemSig.action();
      state = IBot3.State.Busy;
    }
    else if (state == IBot3.State.Busy) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.WaitingForGrant) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.placingBack) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.PostProcessing) {
      this.runtime.illegal.action();
    }
    else this.runtime.illegal.action();
  };

  public void iBot3_placeItemGrantedSig() {
    if (state == IBot3.State.Free) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.WaitingForItem) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.Busy) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.WaitingForGrant) {
      mQTTBot3.in.placeItemGrantedSig.action();
      state = IBot3.State.placingBack;
    }
    else if (state == IBot3.State.placingBack) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.PostProcessing) {
      this.runtime.illegal.action();
    }
    else this.runtime.illegal.action();
  };

  public void iBot3_placeItemDeniedSig() {
    if (state == IBot3.State.Free) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.WaitingForItem) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.Busy) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.WaitingForGrant) {
      mQTTBot3.in.placeItemDeniedSig.action();
      state = IBot3.State.Busy;
    }
    else if (state == IBot3.State.placingBack) {
      this.runtime.illegal.action();
    }
    else if (state == IBot3.State.PostProcessing) {
      this.runtime.illegal.action();
    }
    else this.runtime.illegal.action();
  };

  public void iBot3_reboot() {
    mQTTBot3.in.reboot.action();
    state = IBot3.State.Free;
  };

  public IBot3.State iBot3_getState() {
    reply_IBot3_State = state;
    return reply_IBot3_State;
  };

  public void mQTTBot3_availableSig() {
    if (state == IBot3.State.Busy) {
      state = IBot3.State.Free;
      iBot3.out.availableSig.action();
    }
    else if (state == IBot3.State.PostProcessing) {
      state = IBot3.State.Free;
      iBot3.out.availableSig.action();
    }
    else this.runtime.illegal.action();
  };

  public void mQTTBot3_placeItemSig() {
    if (state == IBot3.State.Busy) {
      state = IBot3.State.WaitingForGrant;
      iBot3.out.placeItemSig.action();
    }
    else this.runtime.illegal.action();
  };

  public void mQTTBot3_placedItemSig() {
    if (state == IBot3.State.placingBack) {
      iBot3.out.placedItemSig.action();
      state = IBot3.State.PostProcessing;
    }
    else this.runtime.illegal.action();
  };

}
