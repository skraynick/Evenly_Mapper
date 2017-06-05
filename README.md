# Evenly_Mapper
## Images
No images were used in the app at this point.

## Concentration of Functionality
I choose to display the name and distance in the home list because
for me, you would want to know the name and how far the venue is from
your current local. It made more sense then address as not everyone may
know the streets so well, but certianly can determine if they want to
go to a place based on how far it is to get there.

For my second, well I was going to go to a map, but then 
reread the instructions, and instead focused on the detail screen.
It is a very basic details screen. It has more indepth information for the user
than the home list. I chose this also, beccause then I could have
and extra screen and show the tie in with the main list, and still
meet time constraints. I really wanted to add in the map,
but ran out of time.

## Libraries I used
- dagger - I used this for depency injection
- butterknife (although didn't get to implementing) -
I like this library for reducing the referencing reduncing in boiler-plate
code. //TODO in the next release.
- Okhttp - I find it works with retrofit - for handing requests
- Retrofit - for network calls and handling requests
- eventbus - simple subscribe/event handling, works well for the use case.

## Common Errors
Not sure if this meant from a UI usage perspective or common pitfuls in the 
components used. Lists always pose a challenge, especially in larger data sets.
one could use pagination for really large sets. for the purpose of the app
this would be a particularly troubling issue.
Offline use is another issue that I didn't not address in this
version. As we need the data that is from the server, we can either cache stuff locally,
or if we wanted to get all fancy we could even store after a loss of internet a copy of 
that moment in time. Not great performance wise, but can be done.
Ease of use. Well this app was easy to navigate, click on an item and go somewhere.
Also the loss of gps and permissions comes to mind, because I didn't to do the map this 
 wasn't so much of an issue for this app, but has been in the past for me.
 Another note would be states of the app, if someone closes the app and starts it up,
 at what state is the app and how do we deal with it. I did not do much in this iteration,
 but this issue pops up time and time again in applications I have worked on. It can
 be mitigated by being mind full where the user is in the flow and where we want them when
 they return; making we gracefully close down components and strarting them up in the state
 in which they are needed.
 
