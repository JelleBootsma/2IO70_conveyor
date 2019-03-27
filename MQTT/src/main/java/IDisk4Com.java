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
class IDisk4Com extends Interface<IDisk4Com.In, IDisk4Com.Out> {
  enum State {
    NotAssigned, WaitForArrival, Arrived
  };
  class In extends Interface.In {
    Action assignedD;
    Action assigned3;
    Action assigned4;
    Action unAssign;
    Action reboot;
  }
  class Out extends Interface.Out {
    Action diskArrived;
  }
  public IDisk4Com() {
    in = new In();
    out = new Out();
  }
}
