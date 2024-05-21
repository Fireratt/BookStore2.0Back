package com.example.myapp;

// this file is used to save the book's information
import com.example.myapp.data.Book ; 
public class BookList {
    static final int size = 6 ; 
    static final Book[] Books = 
    {
        new Book(
            1,
            "ACourtofWingsandRuin",
            "Sarah Janet Maas",
            "Feyre has returned to the Spring Court, determined to gather information on Tamlin's maneuverings and the invading king threatening to bring Prythian to its knees. But to do so she must play a deadly game of deceit—and one slip may spell doom not only for Feyre, but for her world as well. As war bears down upon them all, Feyre must decide who to trust amongst the dazzling and lethal High Lords—and hunt for allies in unexpected places",
            125.0,
            100.0, 
            100
        ),
        new Book(
            2,

            "FourthWing",
            "Rebecca Yarros",
            "Suspenseful, sexy, and with incredibly entertaining storytelling, the first in Yarros' Empyrean series will delight fans of romantic, adventure-filled fantasy.",
            122.0,
            100.0, 

            2
        ),        
        new Book(
            3,

            "HowtoCatchaLeprechaun",
            "Adam Wallace",
            "Celebrate St. Patrick's Day with the #1 New York Times and USA Today Bestselling Laugh-out-loud Holiday Adventure for Kids!" ,
            124.0,
            100.0, 
            30
        ),        
        new Book(
            4,

            "IfOnlyIHadToldHer",
            "Nowlin Laura",
            "Finn has always loved Autumn. She is a constant in his life, except it's in the role of friend, not girlfriend. Finn wants her to see him as more, wants to prove HE should be the man by her side. Even if that means heartache for the people they are each dating. It would be worth the pain to be with right person.",
            120.0,
            100.0, 
            1
        ),        
        new Book(
            5,

            "IronFlame",
            "Rebecca Yarros",
            "Suspenseful, sexy, and with incredibly entertaining storytelling, the first in Yarros' Empyrean series will delight fans of romantic, adventure-filled fantasy." ,
            122.0,
            100.0, 
            2
        ),        
        new Book(
            6,

            "ScientificHealingAffirmation",
            "Paramahansa Yogananda",
            "Long before the use of affirmations was embraced in mainstream settings as diverse as hospitals, recovery programs, sports areneas, and corporate suites, the renowned mystic Paramahansa Yogananda - author of the spiritual classic Autobiography of a Yogi." ,
            121.0,
            100.0, 
            2220
        ),
    } ; 
    public static Book find(String name)
    {
        for (int i = 0 ; i < size ; i++)
        {
            if(name.equals(Books[i].getName()))
            {
                return Books[i] ; 
            }
        }
        return null; 
    }
}
