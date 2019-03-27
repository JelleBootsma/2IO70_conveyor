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
class BeltController extends Component {
  enum State {
    InitialState, SystemEmergency, SystemRunning, Sequence, EndingSequence
  };
  enum Bots {
    bot2, bot3, bot4
  };

  BeltController.State state;
  Boolean bot3GrantedState;
  Boolean bot4GrantedState;
  int sequenceNr;
  int topOfQueue;
  IBeltEngines.State reply_IBeltEngines_State;

  IBot3.State reply_IBot3_State;

  IBot4.State reply_IBot4_State;

  IDisk2.State reply_IDisk2_State;

  IDisk3.State reply_IDisk3_State;

  IDisk4.State reply_IDisk4_State;


  IBeltController iBeltController;
  IBeltEngines iBeltEngines;
  IBot2 iBot2;
  IBot3 iBot3;
  IBot4 iBot4;
  IDisk2 iDisk2;
  IDisk3 iDisk3;
  IDisk4 iDisk4;
  IGate iGate;

  public BeltController(Locator locator) {this(locator, "");};

  public BeltController(Locator locator, String name) {this(locator, name, null);};

  public BeltController(Locator locator, String name, SystemComponent parent) {
    super(locator, name, parent);
    this.flushes = true;
    state = BeltController.State.InitialState;
    bot3GrantedState = false;
    bot4GrantedState = false;
    sequenceNr = 0;
    topOfQueue = 3;
    iBeltController = new IBeltController();
    iBeltController.in.name = "iBeltController";
    iBeltController.in.self = this;
    iBeltEngines = new IBeltEngines();
    iBeltEngines.out.name = "iBeltEngines";
    iBeltEngines.out.self = this;

    iBot2 = new IBot2();
    iBot2.out.name = "iBot2";
    iBot2.out.self = this;

    iBot3 = new IBot3();
    iBot3.out.name = "iBot3";
    iBot3.out.self = this;

    iBot4 = new IBot4();
    iBot4.out.name = "iBot4";
    iBot4.out.self = this;

    iDisk2 = new IDisk2();
    iDisk2.out.name = "iDisk2";
    iDisk2.out.self = this;

    iDisk3 = new IDisk3();
    iDisk3.out.name = "iDisk3";
    iDisk3.out.self = this;

    iDisk4 = new IDisk4();
    iDisk4.out.name = "iDisk4";
    iDisk4.out.self = this;

    iGate = new IGate();
    iGate.out.name = "iGate";
    iGate.out.self = this;

    iBeltController.in.emergency = () -> {Runtime.callIn(this, () -> {iBeltController_emergency();}, new Meta(this.iBeltController, "emergency"));};

    iBeltController.in.reboot = () -> {Runtime.callIn(this, () -> {iBeltController_reboot();}, new Meta(this.iBeltController, "reboot"));};

    iBeltController.in.dropped = () -> {Runtime.callIn(this, () -> {iBeltController_dropped();}, new Meta(this.iBeltController, "dropped"));};

    iBeltController.in.startSequence = () -> {Runtime.callIn(this, () -> {iBeltController_startSequence();}, new Meta(this.iBeltController, "startSequence"));};

    iBot2.out.sequenceReceived = () -> {Runtime.callOut(this, () -> {iBot2_sequenceReceived();}, new Meta(this.iBot2, "sequenceReceived"));};

    iBot2.out.sequenceProcessed = () -> {Runtime.callOut(this, () -> {iBot2_sequenceProcessed();}, new Meta(this.iBot2, "sequenceProcessed"));};

    iBot3.out.availableSig = () -> {Runtime.callOut(this, () -> {iBot3_availableSig();}, new Meta(this.iBot3, "availableSig"));};

    iBot3.out.placeItemSig = () -> {Runtime.callOut(this, () -> {iBot3_placeItemSig();}, new Meta(this.iBot3, "placeItemSig"));};

    iBot3.out.placedItemSig = () -> {Runtime.callOut(this, () -> {iBot3_placedItemSig();}, new Meta(this.iBot3, "placedItemSig"));};

    iBot4.out.availableSig = () -> {Runtime.callOut(this, () -> {iBot4_availableSig();}, new Meta(this.iBot4, "availableSig"));};

    iBot4.out.placeItemSig = () -> {Runtime.callOut(this, () -> {iBot4_placeItemSig();}, new Meta(this.iBot4, "placeItemSig"));};

    iBot4.out.placedItemSig = () -> {Runtime.callOut(this, () -> {iBot4_placedItemSig();}, new Meta(this.iBot4, "placedItemSig"));};

    iDisk2.out.diskArrived = () -> {Runtime.callOut(this, () -> {iDisk2_diskArrived();}, new Meta(this.iDisk2, "diskArrived"));};

    iDisk3.out.diskArrived = () -> {Runtime.callOut(this, () -> {iDisk3_diskArrived();}, new Meta(this.iDisk3, "diskArrived"));};

    iDisk4.out.diskArrived = () -> {Runtime.callOut(this, () -> {iDisk4_diskArrived();}, new Meta(this.iDisk4, "diskArrived"));};

  }
  public void iBeltController_emergency() {
    if (state == BeltController.State.InitialState) {
      iBeltEngines.in.stop.action();
      state = BeltController.State.SystemEmergency;
    }
    else if (state == BeltController.State.SystemRunning) {
      iBeltEngines.in.stop.action();
      state = BeltController.State.SystemEmergency;
    }
    else if (state == BeltController.State.Sequence) {
      iBeltEngines.in.stop.action();
      state = BeltController.State.SystemEmergency;
    }
    else if (state == BeltController.State.EndingSequence) {
      iBeltEngines.in.stop.action();
      state = BeltController.State.SystemEmergency;
    }
    else if (state == BeltController.State.SystemEmergency) { }
    else this.runtime.illegal.action();
  };

  public void iBeltController_reboot() {
    if (state == BeltController.State.InitialState) { }
    else if (state == BeltController.State.SystemRunning) { }
    else if (state == BeltController.State.Sequence) { }
    else if (state == BeltController.State.EndingSequence) { }
    else if (state == BeltController.State.SystemEmergency) {
      iDisk2.in.reboot.action();
      iDisk3.in.reboot.action();
      iDisk4.in.reboot.action();
      iBot2.in.reboot.action();
      iBot3.in.reboot.action();
      iBot4.in.reboot.action();
      state = BeltController.State.InitialState;
    }
    else this.runtime.illegal.action();
  };

  public void iBeltController_dropped() {
    if (state == BeltController.State.InitialState) {
      iBeltEngines.in.start.action();
      V<BeltController.Bots> assignedBot = new V <BeltController.Bots>(getNextBot());
      if (assignedBot.v == BeltController.Bots.bot2) {
        this.runtime.illegal.action();
      }
      if (assignedBot.v == BeltController.Bots.bot3) {
        iDisk3.in.assignedD.action();
        iBot3.in.assigned.action();
        state = BeltController.State.SystemRunning;
      }
      if (assignedBot.v == BeltController.Bots.bot4) {
        iDisk4.in.assignedD.action();
        iBot4.in.assigned.action();
        state = BeltController.State.SystemRunning;
      }
    }
    else if (state == BeltController.State.SystemRunning) {
      V<BeltController.Bots> assignedBot = new V <BeltController.Bots>(getNextBot());
      if (assignedBot.v == BeltController.Bots.bot2) {
        iDisk2.in.assignedD.action();
        iBot2.in.assigned.action();
      }
      if (assignedBot.v == BeltController.Bots.bot3) {
        iDisk3.in.assignedD.action();
        iBot3.in.assigned.action();
      }
      if (assignedBot.v == BeltController.Bots.bot4) {
        iDisk4.in.assignedD.action();
        iBot4.in.assigned.action();
      }
    }
    else if (state == BeltController.State.Sequence) {
      iDisk2.in.assignedD.action();
      iBot2.in.assigned.action();
    }
    else if (state == BeltController.State.EndingSequence) { }
    else if (state == BeltController.State.SystemEmergency) { }
    else this.runtime.illegal.action();
  };

  public void iBeltController_startSequence() {
    if (state == BeltController.State.InitialState) {
      iBeltEngines.in.start.action();
      state = BeltController.State.Sequence;
      iBot2.in.startSequenceSig.action();
    }
    else if (state == BeltController.State.SystemRunning) {
      if ((bot3GrantedState == true || bot4GrantedState == true)) { }
      else {
        state = BeltController.State.Sequence;
        iBot2.in.startSequenceSig.action();
      }
    }
    else if (state == BeltController.State.Sequence) { }
    else if (state == BeltController.State.EndingSequence) { }
    else if (state == BeltController.State.SystemEmergency) { }
    else this.runtime.illegal.action();
  };

  public void iBot2_sequenceReceived() {
    if (state == BeltController.State.Sequence) {
      iBeltEngines.in.stop.action();
      state = BeltController.State.EndingSequence;
    }
    else if (state == BeltController.State.EndingSequence) {
      this.runtime.illegal.action();
    }
    else if (state == BeltController.State.SystemEmergency) { }
    else this.runtime.illegal.action();
  };

  public void iBot2_sequenceProcessed() {
    if (state == BeltController.State.Sequence) {
      this.runtime.illegal.action();
    }
    else if (state == BeltController.State.EndingSequence) {
      iBeltEngines.in.start.action();
      state = BeltController.State.SystemRunning;
    }
    else if (state == BeltController.State.EndingSequence) {
      iBeltEngines.in.start.action();
      state = BeltController.State.SystemRunning;
    }
    else if (state == BeltController.State.SystemEmergency) { }
    else this.runtime.illegal.action();
  };

  public void iBot3_availableSig() {
    if (state == BeltController.State.SystemRunning) {
      V<IDisk3.State> diskState = new V <IDisk3.State>(iDisk3.in.getState.action());
      if (diskState.v == IDisk3.State.NotAssigned) {
        this.runtime.illegal.action();
      }
      iDisk3.in.unAssign.action();
    }
    else if (state == BeltController.State.Sequence) {
      V<IDisk3.State> diskState = new V <IDisk3.State>(iDisk3.in.getState.action());
      if (diskState.v == IDisk3.State.NotAssigned) {
        this.runtime.illegal.action();
      }
      iDisk3.in.unAssign.action();
    }
    else if (state == BeltController.State.EndingSequence) {
      V<IDisk3.State> diskState = new V <IDisk3.State>(iDisk3.in.getState.action());
      if (diskState.v == IDisk3.State.NotAssigned) {
        this.runtime.illegal.action();
      }
      iDisk3.in.unAssign.action();
    }
    else if (state == BeltController.State.SystemEmergency) { }
    else this.runtime.illegal.action();
  };

  public void iBot3_placeItemSig() {
    if (state == BeltController.State.SystemRunning) {
      iBot3.in.placeItemGrantedSig.action();
      bot3GrantedState = true;
    }
    else if (state == BeltController.State.Sequence) {
      iBot3.in.placeItemDeniedSig.action();
    }
    else if (state == BeltController.State.EndingSequence) {
      iBot3.in.placeItemDeniedSig.action();
    }
    else if (state == BeltController.State.SystemEmergency) { }
    else this.runtime.illegal.action();
  };

  public void iBot3_placedItemSig() {
    if (state == BeltController.State.SystemRunning) {
      bot3GrantedState = false;
      V<BeltController.Bots> assignedBot = new V <BeltController.Bots>(getNextBot());
      if (assignedBot.v == BeltController.Bots.bot2) {
        iDisk2.in.assigned3.action();
        iBot2.in.assigned.action();
      }
      if (assignedBot.v == BeltController.Bots.bot3) {
        iDisk3.in.assigned3.action();
        iBot3.in.assigned.action();
      }
      if (assignedBot.v == BeltController.Bots.bot4) {
        iDisk4.in.assigned3.action();
        iBot4.in.assigned.action();
      }
    }
    else if (state == BeltController.State.SystemEmergency) { }
    else this.runtime.illegal.action();
  };

  public void iBot4_availableSig() {
    if (state == BeltController.State.SystemRunning) {
      V<IDisk4.State> diskState = new V <IDisk4.State>(iDisk4.in.getState.action());
      if (diskState.v == IDisk4.State.NotAssigned) {
        this.runtime.illegal.action();
      }
      iDisk4.in.unAssign.action();
    }
    else if (state == BeltController.State.Sequence) {
      V<IDisk4.State> diskState = new V <IDisk4.State>(iDisk4.in.getState.action());
      if (diskState.v == IDisk4.State.NotAssigned) {
        this.runtime.illegal.action();
      }
      iDisk4.in.unAssign.action();
    }
    else if (state == BeltController.State.EndingSequence) {
      V<IDisk4.State> diskState = new V <IDisk4.State>(iDisk4.in.getState.action());
      if (diskState.v == IDisk4.State.NotAssigned) {
        this.runtime.illegal.action();
      }
      iDisk4.in.unAssign.action();
    }
    else if (state == BeltController.State.SystemEmergency) { }
    else this.runtime.illegal.action();
  };

  public void iBot4_placeItemSig() {
    if (state == BeltController.State.SystemRunning) {
      iBot4.in.placeItemGrantedSig.action();
      bot4GrantedState = true;
    }
    else if (state == BeltController.State.Sequence) {
      iBot4.in.placeItemDeniedSig.action();
    }
    else if (state == BeltController.State.EndingSequence) {
      iBot4.in.placeItemDeniedSig.action();
    }
    else if (state == BeltController.State.SystemEmergency) { }
    else this.runtime.illegal.action();
  };

  public void iBot4_placedItemSig() {
    if (state == BeltController.State.SystemRunning) {
      bot3GrantedState = false;
      V<BeltController.Bots> assignedBot = new V <BeltController.Bots>(getNextBot());
      if (assignedBot.v == BeltController.Bots.bot2) {
        iDisk2.in.assigned4.action();
        iBot2.in.assigned.action();
      }
      if (assignedBot.v == BeltController.Bots.bot3) {
        iDisk3.in.assigned4.action();
        iBot3.in.assigned.action();
      }
      if (assignedBot.v == BeltController.Bots.bot4) {
        iDisk4.in.assigned4.action();
        iBot4.in.assigned.action();
      }
    }
    else if (state == BeltController.State.SystemEmergency) { }
    else this.runtime.illegal.action();
  };

  public void iDisk2_diskArrived() {
    if (state == BeltController.State.SystemRunning) {
      iGate.in.openGate.action();
      iGate.in.closeGate.action();
      iBot2.in.takeItemSig.action();
    }
    else if (state == BeltController.State.Sequence) {
      iBot2.in.takeItemSig.action();
      iGate.in.openGate.action();
      iGate.in.closeGate.action();
    }
    else if (state == BeltController.State.EndingSequence) {
      iBot2.in.takeItemSig.action();
      iGate.in.openGate.action();
      iGate.in.closeGate.action();
    }
    else if (state == BeltController.State.SystemEmergency) { }
    else this.runtime.illegal.action();
  };

  public void iDisk3_diskArrived() {
    if (state == BeltController.State.SystemRunning) {
      iBot3.in.takeItemSig.action();
    }
    else if (state == BeltController.State.Sequence) {
      iBot3.in.takeItemSig.action();
    }
    else if (state == BeltController.State.EndingSequence) {
      iBot3.in.takeItemSig.action();
    }
    else if (state == BeltController.State.SystemEmergency) { }
    else this.runtime.illegal.action();
  };

  public void iDisk4_diskArrived() {
    if (state == BeltController.State.SystemRunning) {
      iBot4.in.takeItemSig.action();
    }
    else if (state == BeltController.State.Sequence) {
      iBot4.in.takeItemSig.action();
    }
    else if (state == BeltController.State.EndingSequence) {
      iBot4.in.takeItemSig.action();
    }
    else if (state == BeltController.State.SystemEmergency) { }
    else this.runtime.illegal.action();
  };
  public BeltController.Bots getNextBot () {
    V<IBot3.State> bot3State = new V <IBot3.State>(iBot3.in.getState.action());
    V<IBot4.State> bot4State = new V <IBot4.State>(iBot4.in.getState.action());
    if (topOfQueue == 3 && bot3State.v == IBot3.State.Free) {
      topOfQueue = topOfQueue + 1;
      return BeltController.Bots.bot3;
    }
    if (topOfQueue == 4 && bot4State.v == IBot4.State.Free) {
      topOfQueue = topOfQueue - 1;
      return BeltController.Bots.bot4;
    }
    return BeltController.Bots.bot2;
  };

}
