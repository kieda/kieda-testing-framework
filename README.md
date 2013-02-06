README
-------
<DIV ALIGN = CENTER><i>
This cosmic dance of bursing decadence<br>
And withheld permissions<br>
Twists all our arms collectively<br>
But<br>
If sweetness can win (and it can!)<br>
Then I'll still be here tomorrow<br>
To high five you yesterday, my friend.<br>
Peace.
</i></DIV>
<i> - The Royal Tart Toter </i><br>

--------------------------
Files are typically saved as "bro" files with the file extention ".bro"<br>
There are a few included bro files for painting smileys in mspaint, and typing the famous writing the famous royal tart toter's speech.<br>
Note that these files were initially recorded using my own computer, and so the macros probably won't work unless if your computer is set up like mine! (like windows + taskbar on top, 1600x900 resolution.)<br><br>

You record macros in <code>/src/drivers/MainRecorder</code><br>
Valid input args for <code>MainRecorder</code>:<br>
<code>-m</code> : override TO record mouse events <br>
<code>_m</code> : override TO NOT record mouse events<br>
<p>These args, <code>-m</code> and <code>_m</code> are used in combination with <code>m</code> (for mouse movements) <code>p</code> (for mouse presses), and <code>r</code> (for mouse releases).</p>
<br>
<code>-k</code> : override TO record key events <br>
<code>_k</code> : override TO NOT record key events<br>
<p>These args, <code>-k</code> and <code>_k</code> are used in combination with <code>p</code> (for key presses), and <code>r</code> (for key releases).</p>
<br>
default (i.e. a command that is not recognized) is the output file.<br>
The default setting are<br>
<code>out.bro -m m -m p -m r -k m -k p</code><br>
Future args will support non-stop mode (where the can only exit if he/she presses a specific key, rather than ALT+TAB and losing focus.)<br><br>
<b>Type "<code>exit</code>" into the command line to exit and save your recording.</b>
<br><br>

You play back macros via <code>/src/drivers/MainRepeat</code><br>
The only input arg for <code>MainRepeat</code> is the input file that is to be played back. Sit back and watch the show (of your mouse moving or something)!<br>
