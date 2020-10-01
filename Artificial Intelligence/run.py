from games import TicTacToe, minimax_decision, alphabeta_search, GameState, alphabeta_player, random_player

print("Enter the first row.")
row1 = input().split(' ')
print("Enter the second row.")
row2 = input().split(' ')
print("Enter the third row.")
row3 = input().split(' ')

ticTacToe = TicTacToe()
board = {}
moves=[]
row1.extend(row2)
row1.extend(row3)
table = row1
rowIndex=1
columnIndex=1

# Creating the current state of the game, from the user inputs
for element in table:
    if(element in ['X', 'O']):
        board[(rowIndex, columnIndex)] = element
    else:
        moves.append((rowIndex, columnIndex))
    columnIndex+=1
    if(columnIndex>3):
        rowIndex+=1
        columnIndex=1

# Code to find who has to move next, considering X starts the game
nextToMove='X'
if(table.count('O')!=table.count('X')):
    nextToMove='O'


# Get the utility value by moving the first 
utilityValue = 0
if (len(moves) > 0):
    utilityValue = ticTacToe.compute_utility(board, moves[0], nextToMove)
# create the current game state with the available information
gameState = GameState(to_move = nextToMove, utility = utilityValue, board = board, moves = moves)   

print()
print("moves: ", end="")
# The below print will give all the legal actions possible from this gameState
print(ticTacToe.actions(gameState))

#Below is the alphabeta_player who choses to play optimally
alphabeta_player(ticTacToe, gameState) 

#Below is the random_player who choses to play normally
random_player(ticTacToe, gameState)

print()
print("current game state: ")
#the below command displays the state of game
ticTacToe.display(gameState)

print()
print("================================")
print("Whose turn is it now?")
print(nextToMove)
print("===================================================")
print("How many states did the minimax algorithm evaluate?")
numberOfStates = 0
if (len(moves) > 0):
    optimalMove, numberOfStates= minimax_decision(gameState, ticTacToe, True)
print(numberOfStates)
print("==============================================================")
print("How many states did the alpha-beta pruning algorithm evaluate?")
numberOfStates = 0
if (len(moves) > 0):
    optimalMove, numberOfStates = alphabeta_search(gameState, ticTacToe, True)
print(numberOfStates)
print("=================================================================")

# The below command is to continue game from the state the input was given 
numberOfStates = 0
if (len(moves) > 0):
    utility, board = ticTacToe.continue_game(gameState, False, alphabeta_player, alphabeta_player)

currentGameState = 0
print("What is the value of the current state from the perspective of X?")
if len(moves) == 0:
    getValue=ticTacToe.full_table(board)
    if(getValue =='X'):
        currentGameState = 1
    elif(getValue == 'O'):
        currentGameState = -1
else:
    currentGameState=ticTacToe.utility(board, 'X')
print(currentGameState)
print("=================================================================")
print("Assuming both X and O play optimally, does X have a guaranteed win? Is it a tie? Is it a guaranteed loss for X?")
if(currentGameState==1):
    print("X will win.")
elif(currentGameState==-1):
    print("X will lose.")
elif(currentGameState==0):
    print("It is a tie.")
print("=================================================================")
print("Assuming both X and O would play optimally, how would they play till the game ends?")
ticTacToe.display(gameState)
print()
ticTacToe.continue_game(gameState, True, alphabeta_player, alphabeta_player)
