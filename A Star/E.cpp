#include<iostream>
#include <cmath>
#include <limits.h>
using namespace std;

int g = 0;

void print(int puzzle[])
{
    for(int i = 0; i<9 ; i++)
    {
        if(i%3==0) cout<<"\n";
        if(puzzle[i] == -1) cout<<"_"<<" ";
        else cout<< puzzle[i] << " ";
    }
    cout<<"\n\n";
}

void moveLeft(int start[], int position)
{
    swap(start[position], start[position-1]);
}

void moveRight(int start[], int position)
{
    swap(start[position], start[position+1]);
}

void moveTop(int start[], int position)
{
    swap(start[position], start[position -3]);
}

void moveBottom(int start[], int position)
{
    swap(start[position], start[position +3]);
}

void copy(int temp[],int real[])
{
    for(int i = 0; i < 9; i++)temp[i] = real[i]; 
}

int heuristic(int start[],int goal[])
{
    int h = 0;
    for(int i = 0; i<9; i++)
    {
        for(int j = 0; j < 9; j++)
        {
            if(start[i] == goal[j] && start[i]!= -1)
            {
                h += abs((j-i)/3) + abs((j-i)%3);
            }
        }
    }
    return h+g;
}

void movetile(int start[] , int goal[])
{
    int emptyAt = 0;
    for(int i =0; i<9; i++)
    {
        if(start[i] == -1)
        {
            emptyAt = i;
            break;
        }
    }


    int t1[9],t2[9],t3[9],t4[9],f1 = INT_MAX,f2 = INT_MAX,f3 = INT_MAX,f4 = INT_MAX;
    
    copy(t1,start);
    copy(t2,start);
    copy(t3,start);
    copy(t4,start);


    int row = emptyAt/3;
    int col = emptyAt%3;

    if(col-1 >= 0){
        moveLeft(t1,emptyAt);
        f1 = heuristic(t1,goal);
    }
     if(col+1 <= 3){
        moveRight(t2,emptyAt);
        f2 = heuristic(t2,goal);
    }
     if(row-1 >= 0){
        moveTop(t3,emptyAt);
        f3 = heuristic(t3,goal);
    }
     if(row+1 <= 3){
        moveBottom(t4,emptyAt);
        f4 = heuristic(t4,goal);
    }

    if(f1 < f2 && f1 < f3 && f1 < f4)
    {
        moveLeft(start,emptyAt);
    }
    else if(f2 < f1 && f2 < f3 && f2 < f4)
    {
        moveRight(start,emptyAt);
    }
    else if(f3 < f1 && f3 < f2 && f3 < f4){
        moveTop(start,emptyAt);
    }
    else if(f4 < f1 && f4 < f2 && f4 < f3)
    {
        moveBottom(start,emptyAt);
    }

    
}

void solveEight(int start[],int goal[])
{
    g++;
    movetile(start,goal);
    print(start);
    int f = heuristic(start,goal);
    if(f == g)
    {
        cout << "moves" << f<<endl;
        return;
    }
    solveEight(start , goal);

}
bool solvable(int start[])
{   
    int invrs = 0;
    for(int i;i<9;i++)
    {
        if(start[i]<= 1)continue;
        for(int j = i+ 1;j<9;j++)
        {
            if(start[j] == -1)continue;
            if(start[i]>start[j])invrs++;
        }
    }
    return invrs & 1 ? false : true;    
}

int main()
{
    int start[10],goal[10];
    cout<<"\nEnter the current matrix:";
    for(int i=0; i<9;i++){
        cin>>start[i];
    }

    cout<<"\nEnter the goal matrix:";
    for(int i=0; i<9;i++){
        cin>>goal[i];
    }

    if(solvable(start)){
        solveEight(start,goal);
    }
    
}