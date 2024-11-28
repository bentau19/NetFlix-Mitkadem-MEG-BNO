#ifndef ICommand_H
#define ICommand_H

class ICommand {
    public:
     virtual void execute() = 0;
     virtual void execute(string str) = 0;

};

#endif