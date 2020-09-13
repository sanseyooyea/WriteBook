package maxmc.company.writebook.writebook.command;

import maxmc.company.writebook.writebook.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 * @author SanseYooyea
 */
public class Write implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String prefix = WriteBook.prefix;
        String successMessage = WriteBook.successMessage;

        if (args.length == 1) {
            // the part of command:/write help
            if (selfCommand.help.getCommandText().equalsIgnoreCase(args[0])) {
                sender.sendMessage("§6§l§m------------------§7§lThe Plugin WriteBook's usage:§6§l§m------------------");
                sender.sendMessage(ChatColor.WHITE + "/write help" + ChatColor.RED + ".  (get the usage)");
                sender.sendMessage(ChatColor.WHITE + "/write in [page] [text]" + ChatColor.RED + ".  (write the text to new page in the book)");
                sender.sendMessage(ChatColor.WHITE + "/write auto [text]" + ChatColor.RED + ".  (write the text to the latest page in the book)");
                sender.sendMessage(ChatColor.WHITE + "/write clear [page]" + ChatColor.RED + ".  (clear the page of book)");
                sender.sendMessage(ChatColor.WHITE + "/write settitle [title]" + ChatColor.RED + ".  (set the book's title)");
                sender.sendMessage(ChatColor.WHITE + "/write setauthor [author]" + ChatColor.RED + ".  (set the book's author)");
                sender.sendMessage("§6§l§m------------------§7§lThe Plugin WriteBook's usage:§6§l§m------------------");
                return true;
            }
        }
        if (sender instanceof Player) {
            //Judge whether the command sender is a player
            Player p = (Player) sender;
            //Get input command player
            ItemStack hand = p.getInventory().getItemInHand();
            //Get the items in the player's hands
            if (hand.getType() == Material.BOOK_AND_QUILL || hand.getType() == Material.WRITTEN_BOOK) {
                //Judge whether the items in the player's hands are finished books
                BookMeta book = (BookMeta) hand.getItemMeta();
                //Get the player's book
                if (book != null && p.hasPermission(WriteBook.WRITEBOOK_ADMIN_PERMISSION)) {
                    //Judge whether the player who input the command has permission, holds the book,
                    //and whether the number of parameters is 1
                    if (args.length >= 2) {
                        // the part of command:/write auto [text]
                        if (selfCommand.auto.getCommandText().equalsIgnoreCase(args[0])) {
                            StringBuilder text = new StringBuilder(args[1]);
                            int length = args.length;
                            for (int i = 2; i < length; i++) {
                                text.append("\n").append(args[i]);
                            }
                            //append more args to text
                            if (book.hasTitle() && book.getPageCount() <= 50) {
                                String regentext;
                                regentext = text.toString().replaceAll("&", "§");
                                book.addPage(regentext);
                                hand.setItemMeta(book);
                                p.sendMessage(ChatColor.GOLD + prefix + successMessage);
                            } else {
                                p.sendMessage(ChatColor.GOLD + prefix + "The maximum page limit of the book has been exceeded");
                            }
                            return true;
                        }
                        // the part of command:/write in [page] [text]
                        else if (selfCommand.in.getCommandText().equalsIgnoreCase(args[0])) {
                            String tempPage = args[1];
                            int page = Integer.parseInt(tempPage);
                            if (page > 50) {
                                p.sendMessage(ChatColor.RED + prefix + ">>Too much pages");
                                return true;
                            }
                            StringBuilder text = new StringBuilder(args[2]);
                            int length = args.length;
                            for (int i = 2; i < length; i++) {
                                text.append("\n").append(args[i]);
                            }
                            //append more args to text
                            if (book.hasTitle() && book.getPageCount() <= 50) {
                                String regentext;
                                regentext = text.toString().replaceAll("&", "§");
                                if (book.getPage(page).length() + regentext.length() < 255) {
                                    book.setPage(page, book.getPage(page) + regentext);
                                    p.sendMessage(ChatColor.GOLD + prefix + successMessage);
                                } else {
                                    p.sendMessage(ChatColor.RED + prefix + ">>Have too much words!");
                                }
                                hand.setItemMeta(book);
                            } else {
                                p.sendMessage(ChatColor.GOLD + prefix + "The maximum page limit of the book has been exceeded");
                            }
                            return true;
                        }
                        // the part of command:/write clear [page]
                        else if (selfCommand.clear.getCommandText().equalsIgnoreCase(args[0])){
                            int page = Integer.parseInt(args[1]);
                            int pages = book.getPageCount();
                            if (pages > 0 && page <=50 && page <= pages) {
                                book.setPage(page,"");
                                p.sendMessage(ChatColor.GOLD + prefix + successMessage);
                                return true;
                            }
                        }
                        // the part of command:/write settitle [title]
                        else if (selfCommand.settile.getCommandText().equalsIgnoreCase(args[0])) {
                            int titleLength = args[1].length();
                            if (titleLength <= 16) {
                                String title = args[1];
                                book.setTitle(title);
                                p.sendMessage(ChatColor.GOLD + prefix + ">>Suucceed");
                            } else {
                                p.sendMessage(ChatColor.RED + prefix + ">>The length of your length is too long!");
                            }
                        }
                        // the part of command:/write setauthor [author]
                        else if (selfCommand.setauthor.getCommandText().equalsIgnoreCase(args[0])) {
                            String bookAuthor = args[1];
                            book.setAuthor(bookAuthor);
                            p.sendMessage(ChatColor.GOLD + prefix + ">>Suucceed");
                        }
                        sender.sendMessage(ChatColor.GOLD + prefix + ">>No this command,Please use command: '/write help' to get know the usage");
                        return true;
                    }
                    sender.sendMessage(ChatColor.GOLD + prefix + ">>The number of parameters you entered is wrong or there is no corresponding book or permission");
                    return true;
                }
                sender.sendMessage(ChatColor.GOLD + prefix + ">>You should take a Written Book");
                return true;
            }
            sender.sendMessage(ChatColor.GOLD + prefix + ">>Only player can use this command");
            return true;
        }
        sender.sendMessage(ChatColor.GOLD + prefix + ">>Unknown Error");
        return true;
    }
}