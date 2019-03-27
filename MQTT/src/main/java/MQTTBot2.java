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
class MQTTBot2 extends Interface<MQTTBot2.In, MQTTBot2.Out> {
  enum State {
    Running, WaitingForSequence, ProcessingSequence
  };
  class In extends Interface.In {
    Action takeItemSig;
    Action startSequenceSig;
    Action reboot;
    Action takeItem;
  }
  class Out extends Interface.Out {
    Action sequenceReceived;
    Action sequenceProcessed;
  }
  public MQTTBot2() {
    in = new In();
    out = new Out();
  }
}
