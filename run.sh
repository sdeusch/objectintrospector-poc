#!/bin/bash

#################################################################################
# Name of Job: run.sh
# Purpose: Introspect Object Values  
# Author: Stefan Deusch
# $Revision: $
#################################################################################


cygwin=false
case "`uname`" in
CYGWIN*) cygwin=true;
esac

echo ${cygwin}

APP_FULL_NAME=`which ${0}`
SCRIPTS_DIR=${APP_FULL_NAME%/*}

ARGUMENTS=$*

HOME_DIR=$SCRIPTS_DIR
LIB_DIR=$SCRIPTS_DIR/lib


if [ ${cygwin} = 'true' ]; then
cp=`find ${LIB_DIR}/*.jar -name '*.jar' |xargs echo  |tr ' ' ':'`
CP_PATH=${CONF_DIR}:${cp}
else
cp=`find ${LIB_DIR}/*.jar -name '*.jar' |xargs echo  |tr ' ' ':'`
CP_PATH=.:${cp}:bin
fi

echo "Using Classpath: ${CP_PATH}"


if $cygwin; then
[ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
fi
echo ${JAVA_HOME}
if [ !  -z "${JAVA_HOME}"  ]; then
JAVA_FULL_NAME="$JAVA_HOME/bin/java"
fi

echo ${JAVA_FULL_NAME}
if $cygwin; then
[ -n "$JAVA_FULL_NAME" ] && JAVA_HOME=`cygpath --unix "$JAVA_FULL_NAME"`
fi

if [ -z "${JAVA_FULL_NAME}" ] || [ ! -x "${JAVA_FULL_NAME}" ]; then
echo "Unable to locate Java VM. Set JAVA_HOME for Java 6+ in your environment."
exit 1
fi

echo "Using version :"${JAVA_FULL_NAME}


if [ ${cygwin} = 'true' ]; then
CP_PATH=`cygpath -d --path $CP_PATH`
fi

CMD="${JAVA_FULL_NAME} -cp $CP_PATH introspect.ObjectShell"

echo "Now running: $CMD"

$CMD

