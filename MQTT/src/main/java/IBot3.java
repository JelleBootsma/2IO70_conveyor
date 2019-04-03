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
class IBot3 extends Interface<IBot3.In, IBot3.Out> {
  enum State {
    Free, WaitingForItem, Busy, WaitingForGrant, placingBack, PostProcessing
  };
  class In extends Interface.In {
    Action assigned;
    Action takeItemSig;
    Action placeItemGrantedSig;
    Action placeItemDeniedSig;
    Action reboot;
    ValuedAction<IBot3.State> getState;
  }
  class Out extends Interface.Out {
    Action availableSig;
    Action placeItemSig;
    Action placedItemSig;
    Action emergency;
  }
  public IBot3() {
    in = new In();
    out = new Out();
  }
}
