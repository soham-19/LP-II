% Define some facts about the chatbots knowledge
fact(greet, ['Hello.', 'Hi there!', 'Greetings!']).
fact(goodbye, ['Goodbye!', 'See you later.', 'Farewell!']).
fact(ask_how_are_you, ['I am doing well, thank you.', 'Feeling good, thanks for asking.', 'I\'m functioning within normal parameters.']).
fact(joke, ['Why don\'t scientists trust atoms? Because they make up everything!', 'I told my wife she was drawing her eyebrows too high. She looked surprised!']).

% Define rules for processing user input
process_input(Input, Response) :-
    % Convert input to lowercase
    downcase_atom(Input, LowercaseInput),
   
    % Check for greetings
    member(LowercaseInput, ['hello', 'hi', 'hey']),
    fact(greet, Responses),
    random_member(Response, Responses).

process_input(Input, Response) :-
    % Convert input to lowercase
    downcase_atom(Input, LowercaseInput),
   
    % Check for goodbye
    member(LowercaseInput, ['goodbye', 'bye', 'see you']),
    fact(goodbye, Responses),
    random_member(Response, Responses).

process_input(Input, Response) :-
    % Convert input to lowercase
    downcase_atom(Input, LowercaseInput),
   
    % Check for how are you
    member(LowercaseInput, ['how are you', 'how are you doing']),
    fact(ask_how_are_you, Responses),
    random_member(Response, Responses).

process_input(Input, Response) :-
    % Convert input to lowercase
    downcase_atom(Input, LowercaseInput),
   
    % Check for jokes
    member(LowercaseInput, ['tell me a joke', 'joke', 'say something funny']),
    fact(joke, Responses),
    random_member(Response, Responses).


% Main loop for conversation
chat :-
    write('Welcome! How can I assist you?'), nl,
    repeat,
    write('> '),
    read_line_to_string(user_input, Input),
    process_input(Input, Response),
    write(Response), nl,
    (Input = 'goodbye' ; Input = 'bye'), !.