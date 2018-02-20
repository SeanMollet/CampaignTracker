using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Security.Cryptography;
using System.Text.RegularExpressions;

namespace CampaignData
{
    public enum RollType
    {
        Normal,
        Advantage,
        Disadvantage
    }
    public static class Dice
    {
        static private RNGCryptoServiceProvider rand;
        static Dice()
        {
            rand = new RNGCryptoServiceProvider();
        }

        public static int Roll(int DiceSize, RollType rollType)
        {
            byte diceSize = (byte)DiceSize;
            int firstRoll = RollDice(diceSize);

            switch (rollType)
            {
                default:
                case RollType.Normal:
                    return firstRoll;
                case RollType.Advantage:
                    int advRoll = RollDice(diceSize);
                    if (advRoll > firstRoll)
                    {
                        return advRoll;
                    }
                    return firstRoll;
                case RollType.Disadvantage:
                    int disadvRoll = RollDice(diceSize);
                    if (disadvRoll < firstRoll)
                    {
                        return disadvRoll;
                    }
                    return firstRoll;
            }
        }

        private static byte RollDice(byte numberSides)
        {
            if (numberSides <= 0)
                throw new ArgumentOutOfRangeException("numberSides");

            // Create a byte array to hold the random value.
            byte[] randomNumber = new byte[1];
            do
            {
                // Fill the array with a random value.
                rand.GetBytes(randomNumber);
            }
            while (!IsFairRoll(randomNumber[0], numberSides));
            // Return the random number mod the number
            // of sides.  The possible values are zero-
            // based, so we add one.
            return (byte)((randomNumber[0] % numberSides) + 1);
        }

        private static bool IsFairRoll(byte roll, byte numSides)
        {
            // There are MaxValue / numSides full sets of numbers that can come up
            // in a single byte.  For instance, if we have a 6 sided die, there are
            // 42 full sets of 1-6 that come up.  The 43rd set is incomplete.
            int fullSetsOfValues = Byte.MaxValue / numSides;

            // If the roll is within this range of fair values, then we let it continue.
            // In the 6 sided die case, a roll between 0 and 251 is allowed.  (We use
            // < rather than <= since the = portion allows through an extra 0 value).
            // 252 through 255 would provide an extra 0, 1, 2, 3 so they are not fair
            // to use.
            return roll < numSides * fullSetsOfValues;
        }
    }

    public static class DiceUtilities
    {
        static Regex reg;
        static DiceUtilities()
        {
            reg = new Regex(@"\((\d+?)d(\d+?)([+-]*)(\d*?)\)");
        }
        public static HitDice GetHitDice(string HitString)
        {
            var hit = new HitDice();
            var result = reg.Matches(HitString);
            if (result.Count > 0)
            {
                if (result[0].Groups.Count >= 5)
                {
                    bool success = true;

                    int HitDiceCount=0;
                    int HitDiceSize = 0;
                    int HitModifier = 0;

                    success &= int.TryParse(result[0].Groups[1].Value, out HitDiceCount);
                    success &= int.TryParse(result[0].Groups[2].Value, out HitDiceSize);

                    if (result[0].Groups[3].Value.Length>0 && result[0].Groups[4].Value.Length > 0)
                    {
                        success &= int.TryParse(result[0].Groups[4].Value, out HitModifier);
                        //Make it negative if it needs to be
                        if (result[0].Groups[3].Value == "-")
                        {
                            HitModifier *= -1;
                        }
                    }
                    if (success)
                    {
                        hit.HitDiceCount = HitDiceCount;
                        hit.HitDiceSize = HitDiceSize;
                        hit.HitModifier = HitModifier;
                    }
                }
            }
            return hit;
        }
    }
    public class HitDice
    {
        public int HitDiceCount { get; set; }
        public int HitDiceSize { get; set; }
        public int HitModifier { get; set; }
    }
}
