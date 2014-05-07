Semantic-Network
================

This is a making/searching semantic network system with Java.

##How to use
####make link

    S.jobs,is-a,Apple's Founder
    
This command make a link which is [S.Jobs]--[is-a]--[Apple's Founder].

####search link

    ?x,is-a,Apple's Founder
    
This command means "Who is a Apple's Founder".  
In this case, `S.Jobs` is returned.  
`?x` means like "What" and "Who" etc. 

In addition, you can ask True/False questions. Such as

    showwin,is-a,Apple's Founder?

In this case, `False` is returned.  
You have to add `?` at last.