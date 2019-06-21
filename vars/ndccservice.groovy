def triggerBuildArray = []
    def buildJobArray = []
    def jobArray = []
    def paramsArray = []
    def noOfJob = 2 

    //function to trigger job

    def triggerJob(def job, def params, def jobNo, def buildJobArray) {

    buildJobArray << job.scheduleBuild2(0, new Cause.UpstreamCause(build), new ParametersAction(params))

    println"triggered job "+jobNo;
    println"waiting for completion of job "+jobNo;

   }

    jobArray << Hudson.instance.getJob('job1');


    //define parameters

    paramsArray << [            
    new StringParameterValue('baseurl',build.getEnvironment(listener).get('ORAbaseurl')),
    new StringParameterValue('firm',build.getEnvironment(listener).get('ORAfirm')),
    new StringParameterValue('loginname',build.getEnvironment(listener).get('ORAloginname'))

    ]

    for(int i=0;i<noOfJob;i++)
    {
        triggerJob(jobArray[i],paramsArray[i],i+1, buildJobArray);

    }
