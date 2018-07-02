#! /bin/sh

usage="$(basename $0) -g groupID -a artifactID -v version -f file"
while getopts :g:a:v:f:p: option
do
    case $option in
	g)
	    groupID=$OPTARG
	    ;;
	a)
	    artifactID=$OPTARG
	    ;;
	v)
	    version=$OPTARG
	    ;;
	f)
	    file=$OPTARG
	    ;;
	p)
	    pomFile=$OPTARG
	    ;;
	*)
	    echo $usage
	    exit 1
    esac
done

if [ "$groupID" = "" -o "$artifactID" = "" -o "$version" = "" -o "$file" = "" ]
then
    echo $usage
    exit 1
fi

mvnArgs="-DgroupId=$groupID -DartifactId=$artifactID -Dversion=$version -Durl=file:./lib  -Dfile=$file"
[ "$pomFile" != "" ] && mvnArgs="$mvnArgs -DpomFile=$pomFile"

mvn deploy:deploy-file $mvnArgs
mvn clean install -U # force the mvn update
