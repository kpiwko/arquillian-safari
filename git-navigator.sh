#!/bin/bash

rewind() {
    git rev-list HEAD > .git-navigator-list
    head -n 1 .git-navigator-list > .git-head
    next
}

next() {
    git checkout `tail -n 1 .git-navigator-list`
    head -n $((`cat .git-navigator-list | wc -l` - 1)) .git-navigator-list > .git-navigator-list-temp
    mv .git-navigator-list-temp .git-navigator-list
}

forward() {
    git checkout `cat .git-head`
}

help() {
    echo "Use" `basename $0` "rewind to rewind history for the very first commit and start with the example"
    echo "Use" `basename $0` "next to move to the next step in the tutorial"
}

case $@ in 
    rewind) rewind ;;
    next) next;;
    forward) forward;;
    *) help;;
esac
