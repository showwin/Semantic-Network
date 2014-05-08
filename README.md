Semantic-Network
================

This is a making/searching semantic network system with Java.

--------------------------------

## How to use
#### Start Program

    javac Main.java
    java Main
    
After that, you can use following commands at command line.

--------------------------------

#### Setup

    setUp

This will make following links:  
[S.Jobs]--[is-a]--[Former_Apple_CEO]  
[S.Jobs]--[likes]--[The_Beatles]  
[S.Jobs]--[is-a]--[Apple's_Founder]  
[Apple's_Founder]--[is-a]--[Genius]  
[S.Wozniak]--[is-a]--[Apple's_Founder]  
[S.Wozniak]--[has-a]--[Doctor_of_Science]  


#### Display network(all links)

    show
    
This command display network.


#### Make link

    S.Jobs is-a Apple's_Founder
    
This command make a link which is [S.Jobs]--[is-a]--[Apple's Founder].


#### "what" search

    ?x likes The_Beatles
    
This command means "Who is a Apple's Founder".  
In this case, `S.Jobs` is returned.  
`?x` means like "What" and "Who" etc. 


#### True/False search

    showwin is-a Apple's_Founder?

In this case, `False` is returned.  
You have to add `?` at last.


#### AND/OR search

    S.Jobs is-a Apple's_Founder? OR S.Jobs is-a MicroSoft's_Founder?

This returns `True`.

    ?x is-a Apple's_Founder AND ?x likes The_Beatles

This returns `S.Jobs`.


#### Deep search
In this case, following twe links exist:  
　[S.Wozniak]--[is-a]--[Apple's_Founder]  
　[Apple's_Founder]--[is-a]--[Genius]  
So,

    S.Wozniak is-a ?x

This returns `Apple's_Founder AND Genius`.


#### Finish

    end
   
This commands end a command line.