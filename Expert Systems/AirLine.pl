:- dynamic(flight_schedule/4).
:- dynamic(cargo_schedule/4).

% Facts
flight_schedule('F1', 'A', '08:00', '12:00').
flight_schedule('F2', 'B', '05:00', '10:00').
flight_schedule('F3', 'C', '09:00', '14:00').
flight_schedule('F4', 'A', '12:00', '17:00').
flight_schedule('F5', 'B', '10:00', '19:00').


cargo_schedule('C1', 'A', '08:00', '12:00').
cargo_schedule('C2', 'B', '05:00', '10:00').
cargo_schedule('C3', 'C', '09:00', '14:00').
cargo_schedule('C4', 'A', '12:00', '17:00').
cargo_schedule('C5', 'B', '10:00', '19:00').

% find the schedule
find_schedule('flight', Flight, Dest, Dept, Arriv) :-
    flight_schedule(Flight, Dest, Dept, Arriv), !.

find_schedule('cargo', Cargo, Dest, Dept, Arriv) :-
    cargo_schedule(Cargo, Dest, Dept, Arriv), !.

find_schedule(_, _, _, _, _) :-
    write('Item not found int the schedule'), nl.

% add item
add_item('flight', Flight, Dest, Dept, Arriv) :-
    assertz(flight_schedule(Flight, Dest, Dept, Arriv)),
    write('Flight added successfully'), nl.

add_item('cargo', Cargo, Dest, Dept, Arriv) :-
    assertz(cargo_schedule(Cargo, Dest, Dept, Arriv)),
    write('Cargo added successfully'), nl.

% update item
update_item('flight', Flight, Dest, Dept, Arriv) :-
    retract(flight_schedule(Flight, _, _, _)), % retract only the flight with the specified Flight code
    !,
    assertz(flight_schedule(Flight, Dest, Dept, Arriv)),
    !,
    write('Flight updated successfully'), nl,
    !. % Cut to prevent backtracking and updating multiple flights

update_item('cargo', Cargo, Dest, Dept, Arriv) :-
    retract(cargo_schedule(Cargo, _, _, _)),
    assertz(cargo_schedule(Cargo, Dest, Dept, Arriv)),
    write('Cargo updated successfully'), nl,
    !. % Cut to prevent backtracking and updating multiple cargos




% delete item
delete_item('flight', Flight) :-
    retract(flight_schedule(Flight, _, _, _)),
    write('Flight deleted successfully'), nl.

delete_item('cargo', Cargo) :-
    retract(cargo_schedule(Cargo, _, _, _)),
    write('Cargo deleted successfully'), nl.

print_all_flights :-
    write('Flight Schedules:'), nl,
    forall(
        flight_schedule(Flight, Dest, Dept, Arriv),
        writeln('Flight '(Flight, Dest, Dept, Arriv))
    ).
print_all_cargos :-
    write('Cargo Schedules:'), nl,
    forall(
        flight_schedule(Cargo, Dest, Dept, Arriv),
        writeln('Cargo '(Cargo, Dest, Dept, Arriv))
    ).

% menu
menu :-
    repeat,
    nl,
    write('---------- Airline & Cargo System ----------'), nl,
    write('1. Find Flight Schedule'), nl,
    write('2. Find Cargo Schedule'), nl,
    write('3. Add Flight Schedule'), nl,
    write('4. Add Cargo Schedule'), nl,
    write('5. Update Flight Schedule'), nl,
    write('6. Update Cargo Schedule'), nl,
    write('7. Delete Flight Schedule'), nl,
    write('8. Delete Cargo Schedule'), nl,
    write('9. Print All Flight Schedules'), nl,
    write('10. Print All Cargo Schedules'), nl,
    write('11. Exit'), nl,
    write('-------------------------'), nl,
    write('Enter your choice (1-11) : '),

    read_line_to_codes(user_input, ChoiceInput),
    atom_codes(ChoiceAtom, ChoiceInput),
    atom_number(ChoiceAtom, Choice),
    process_choice(Choice),
    Choice == 11, !.

% process your choice
process_choice(1) :-
    nl, write('Enter Flight Code: '),
    read_line_to_codes(user_input, FlightInput),
    atom_codes(FlightAtom, FlightInput),
    find_schedule('flight', FlightAtom, Dest, Dept, Arriv),
    write('Dest : '), write(Dest), nl,
    write('Dept : '), write(Dept), nl,
    write('Arriv : '), write(Arriv), nl, nl.


process_choice(2) :-
    nl, write('Enter Cargo Code '),
    read_line_to_codes(user_input, CargoInput),
    atom_codes(CargoAtom, CargoInput),
    find_schedule('cargo', CargoAtom, Dest, Dept, Arriv),
    write('Dest : '), write(Dest), nl,
    write('Dept : '), write(Dept), nl,
    write('Arriv : '), write(Arriv), nl, nl.

process_choice(3) :-
    nl, write('Enter Flight Code: '),
    read(Flight),
    
    nl, write('Enter Flight Dest: '),
    read(Dest),
    
    nl, write('Enter Flight Dept (HH:MM): '),
    read(DepartureTime),
    
    nl, write('Enter Flight Arrival (HH:MM): '),
    read(ArrivalTime),
    
    add_item('flight', Flight, Dest, DepartureTime, ArrivalTime).


process_choice(4) :-
    nl, write('Enter Cargo Code: '),
    read(Cargo),
    
    nl, write('Enter Cargo Dest: '),
    read(Dest),
    
    nl, write('Enter Cargo Dept (HH:MM): '),
    read(DepartureTime),
    
    nl, write('Enter Flight Arrival (HH:MM): '),
    read(ArrivalTime),
    
    add_item('cargo', Cargo, Dest, DepartureTime, ArrivalTime).

process_choice(5) :-
    nl, write('Enter New Flight Code: '),
    read(Flight),
    
    nl, write('Enter New Flight Dest: '),
    read(Dest),
    
    nl, write('Enter New Flight Dept (HH:MM): '),
    read(DepartureTime),
    
    nl, write('Enter New Flight Arrival (HH:MM): '),
    read(ArrivalTime),
    
    update_item('flight', Flight, Dest, DepartureTime, ArrivalTime).


process_choice(6) :-
    nl, write('Enter New Cargo Code: '),
    read(Cargo),
    
    nl, write('Enter New Cargo Dest: '),
    read(Dest),
    
    nl, write('Enter New Cargo Dept (HH:MM): '),
    read(DepartureTime),
    
    nl, write('Enter New Cargo Arrival (HH:MM): '),
    read(ArrivalTime),
    
    update_item('cargo', Cargo, Dest, DepartureTime, ArrivalTime).


process_choice(7) :-
    nl, write('Enter Flight Code '),
    read_line_to_codes(user_input, FlightInput),
    atom_codes(Flight, FlightInput),
    delete_item('flight', Flight).

process_choice(8) :-
   	nl, write('Enter Cargo Code '),
    read_line_to_codes(user_input, FlightInput),
    atom_codes(Flight, FlightInput),
    delete_item('cargo', Flight).

process_choice(9) :-
    print_all_flights.

process_choice(10) :-
    print_all_cargos.

% Add the rest of the choice predicates here (1 to 8)

process_choice(11) :-
    nl, write('Exiting..'), nl.

% Entry point
:- initialization(menu).