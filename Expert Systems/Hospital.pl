% Dynamic predicates to store information
:- dynamic(facility/2).

% Initial facts (facility, description)
facility('Hospital A', 'A large general hospital with specialized departments for various medical conditions.').
facility('Clinic B', 'A small clinic providing primary care services such as general check-ups and vaccinations.').
facility('Diagnostic Center C', 'A diagnostic center equipped with advanced imaging and laboratory facilities for accurate diagnosis.').
facility('Pharmacy D', 'A pharmacy offering a wide range of medications and healthcare products.').
facility('Rehabilitation Center E', 'A rehabilitation center providing physical therapy and rehabilitation services for patients recovering from injuries or surgeries.').
facility('Emergency Room F', 'An emergency room equipped to handle medical emergencies and provide immediate care to patients in critical condition.').



% Find facility description
find_facility_description(Facility, Description) :-
    facility(Facility, Description), !.

find_facility_description(_, 'Facility not found in the database.').

% Add new facility
add_facility(Facility, Description) :-
    assertz(facility(Facility, Description)),
    write('New facility added successfully.'), nl.

% Remove facility
remove_facility(Facility) :-
    retractall(facility(Facility, _)),
    write('Facility removed successfully.'), nl.

% Print all facilities
print_all_facilities :-
    write('List of All Facilities:'), nl,
    forall(
        facility(Facility, Description),
        (
            write('Facility: '), write(Facility), nl,
            write('Description: '), write(Description), nl, nl
        )
    ).






% Menu
menu :-
    repeat,
    nl, write('---------- Hospital and Medical Facilities ----------'), nl,
    write('1. Find Facility Description'), nl,
    write('2. Add New Facility'), nl,
    write('3. Remove Facility'), nl,
    write('4. Print All Facilities'), nl,
    write('5. Exit'), nl,
    write('-----------------------------------------------------'), nl,
    write('Enter your choice (1-5): '),
    read_line_to_codes(user_input, ChoiceInput),
    atom_codes(ChoiceAtom, ChoiceInput),
    atom_number(ChoiceAtom, Choice),
    process_choice(Choice),
    Choice == 5, !.

% Process user choice
process_choice(1) :-
    nl, write('Enter facility name: '),
    read_line_to_codes(user_input, FacilityInput),
    atom_codes(FacilityAtom, FacilityInput),
    find_facility_description(FacilityAtom, Description),
    write('Description: '), write(Description), nl.

process_choice(2) :-
    nl, write('Enter new facility name: '),
    read_line_to_codes(user_input, NewFacilityInput),
    atom_codes(NewFacilityAtom, NewFacilityInput),
    nl, write('Enter facility description: '),
    read_line_to_codes(user_input, DescriptionInput),
    atom_codes(DescriptionAtom, DescriptionInput),
    add_facility(NewFacilityAtom, DescriptionAtom).

process_choice(3) :-
    nl, write('Enter facility name to remove: '),
    read_line_to_codes(user_input, FacilityInput),
    atom_codes(FacilityAtom, FacilityInput),
    remove_facility(FacilityAtom).

process_choice(4) :-
    print_all_facilities.

process_choice(5) :-
    nl, write('Exiting...'), nl.

% Entry point
:- initialization(menu).