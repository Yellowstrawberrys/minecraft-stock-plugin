package cf.thdisstudio.stock;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.logging.Logger;

public final class Stock extends JavaPlugin implements Listener {
    private static final Logger log = Logger.getLogger("Minecraft");
    stk p1 = new stk();

    Inventory inv = Bukkit.createInventory(null, 45, "주식 (5분마다 가격 변동)");
    private static Economy econ = null;
    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Plugin startup logic
        p1.setPriority(Thread.MIN_PRIORITY);
        p1.start();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String [] args) {
        Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("stock")){


            /*태두리 부분*/
            inv.setItem(0, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(1, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(2, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(3, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(4, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(5, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(6, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(7, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(8, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

            inv.setItem(9, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(18, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(27, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(36, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(17, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(26, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(35, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(44, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

            inv.setItem(37, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(38, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(39, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(40, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(41, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(42, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            inv.setItem(43, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));


            /*스디스 스튜디오 주식*/
            ItemStack thdis = new ItemStack(Material.NETHER_STAR);
            ItemMeta thdism = thdis.getItemMeta();
            thdism.setDisplayName("스디스 스튜디오(서버 개발사)");
            thdism.setLore(Arrays.asList("가격:", String.valueOf(p1.thdisstudio),"변동 일:", p1.formattedDate, "좌클릭으로 구매, 우클릭으로 판매 하세요!"));
            thdis.setItemMeta(thdism);
            inv.setItem(10, thdis);

            /*열매 농장 주식*/
            ItemStack ys = new ItemStack(Material.GOLDEN_CARROT);
            ItemMeta ym = thdis.getItemMeta();
            ym.setDisplayName("열매 농장");
            ym.setLore(Arrays.asList("가격:", String.valueOf(p1.yalmefarm),"변동 일:", p1.formattedDate, "좌클릭으로 구매, 우클릭으로 판매 하세요!"));
            ys.setItemMeta(thdism);
            inv.setItem(11, ys);

            /*스디스 스튜디오 주식*/
            ItemStack mines = new ItemStack(Material.GRASS_BLOCK);
            ItemMeta minem = thdis.getItemMeta();
            minem.setDisplayName("마인크래프트(모장)");
            minem.setLore(Arrays.asList("가격:", String.valueOf(p1.minecraft),"변동 일:", p1.formattedDate, "좌클릭으로 구매, 우클릭으로 판매 하세요!"));
            mines.setItemMeta(minem);
            inv.setItem(12, mines);
            p.openInventory(inv);
        }

        return false;
    }


    @EventHandler
    public void event(InventoryClickEvent e){
        if(e.getInventory().equals(inv)){
            if(e.getClick().isLeftClick()){
                e.setCancelled(true);
                if(e.getSlot() == 10){
//                    e.getWhoClicked().sendMessage(String.valueOf(p1.thdisstudio));
                    if(econ.has(e.getWhoClicked().getName(),Double.valueOf(p1.thdisstudio))){
                        LocalDateTime myDateObj = LocalDateTime.now();
                        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM월dd일, HH시mm분ss초");
                        String formattedDate = myDateObj.format(myFormatObj);
                        ItemStack stk = new ItemStack(Material.PAPER);
                        ItemMeta stkm = stk.getItemMeta();
                        NamespacedKey key = new NamespacedKey(this, "thdisstudio");
                        stkm.setDisplayName("스디스 스튜디오 주식");
                        stkm.setLore(Arrays.asList("구입한 가격: " + String.valueOf(p1.thdisstudio)));
//                        stkm.setLore(Arrays.asList("구입한 가격:", String.valueOf(p1.thdisstudio),"구입한 날짜:", formattedDate,"마지막 가격변동 날짜:", p1.myFormatObj.toString()));
                        stkm.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
                        stk.setItemMeta(stkm);
                        e.getWhoClicked().getInventory().addItem(stk);
                        econ.withdrawPlayer(e.getWhoClicked().getName(), p1.thdisstudio);
                    }else{
                        e.getWhoClicked().sendMessage("스디스 스튜디오 주식을 살 돈이 없습니다");
                    }
                }else if(e.getSlot() == 11){
                    if(econ.has(e.getWhoClicked().getName(),Double.valueOf(p1.yalmefarm))){
                        LocalDateTime myDateObj = LocalDateTime.now();
                        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM월dd일, HH시mm분ss초");
                        ItemStack stk = new ItemStack(Material.PAPER);
                        ItemMeta stkm = stk.getItemMeta();
                        String formattedDate = myDateObj.format(myFormatObj);
                        NamespacedKey key = new NamespacedKey(this, "yalmefarm");
                        stkm.setDisplayName("열매 농장 주식");
                        stkm.setLore(Arrays.asList("구입한 가격: " + String.valueOf(p1.yalmefarm)));
//                        stkm.setLore(Arrays.asList("구입한 가격:", String.valueOf(p1.yalmefarm),"구입한 날짜:", formattedDate, "마지막 가격변동 날짜:", p1.myFormatObj.toString()));
                        stkm.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
                        stk.setItemMeta(stkm);
                        e.getWhoClicked().getInventory().addItem(stk);
                        econ.withdrawPlayer(e.getWhoClicked().getName(), Double.valueOf(p1.yalmefarm));
                    }else{
                        e.getWhoClicked().sendMessage("열매 농장 주식을 살 돈이 없습니다");
                    }
                }else if(e.getSlot() == 12){
                    if(econ.has(e.getWhoClicked().getName(),Double.valueOf(p1.minecraft))){
                        LocalDateTime myDateObj = LocalDateTime.now();
                        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM월dd일, HH시mm분ss초");
                        ItemStack stk = new ItemStack(Material.PAPER);
                        ItemMeta stkm = stk.getItemMeta();
                        String formattedDate = myDateObj.format(myFormatObj);
                        NamespacedKey key = new NamespacedKey(this, "minecraft");
                        stkm.setDisplayName("마인크래프트(모장) 주식");
                        stkm.setLore(Arrays.asList("구입한 가격: " + String.valueOf(p1.minecraft)));
//                        stkm.setLore(Arrays.asList("구입한 가격:", String.valueOf(p1.minecraft),"구입한 날짜:", formattedDate,"마지막 가격변동 날짜:", p1.myFormatObj.toString()));
                        stkm.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
                        stk.setItemMeta(stkm);
                        e.getWhoClicked().getInventory().addItem(stk);
                        econ.withdrawPlayer(e.getWhoClicked().getName(), Double.valueOf(p1.minecraft));
                    }else{
                        e.getWhoClicked().sendMessage("마인크래프트(모장) 주식을 살 돈이 없습니다");
                    }
                }
                e.setCancelled(true);
            }else if(e.isRightClick()){
                e.getWhoClicked().sendMessage("노딸바보");

                NamespacedKey key = new NamespacedKey(this, "thdisstudio");
                NamespacedKey keys = new NamespacedKey(this, "yalmefarm");
                NamespacedKey keyss = new NamespacedKey(this, "minecraft");
                if(e.getWhoClicked().getInventory().getItemInOffHand().getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.INTEGER) == null){
                    e.getWhoClicked().sendMessage("왼쪽 손에 있는 주식을 찾을 수 없습니다");
                }else if(e.getWhoClicked().getInventory().getItemInOffHand().getItemMeta().getPersistentDataContainer().get(keys, PersistentDataType.INTEGER) == null){
                    e.getWhoClicked().sendMessage("왼쪽 손에 있는 주식을 찾을 수 없습니다");
                }else if(e.getWhoClicked().getInventory().getItemInOffHand().getItemMeta().getPersistentDataContainer().get(keyss, PersistentDataType.INTEGER) == null){
                    e.getWhoClicked().sendMessage("왼쪽 손에 있는 주식을 찾을 수 없습니다");
                }else if(e.getWhoClicked().getInventory().getItemInOffHand().getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.INTEGER) == 1){
                    econ.depositPlayer(e.getWhoClicked().getName(),p1.thdisstudio * e.getWhoClicked().getInventory().getItemInOffHand().getAmount());
                    e.getWhoClicked().sendMessage("성공적으로 판매하였습니다!");
                }else if(e.getWhoClicked().getInventory().getItemInOffHand().getItemMeta().getPersistentDataContainer().get(keys, PersistentDataType.INTEGER) == 1){
                    econ.depositPlayer(e.getWhoClicked().getName(),p1.yalmefarm * e.getWhoClicked().getInventory().getItemInOffHand().getAmount());
                    e.getWhoClicked().sendMessage("성공적으로 판매하였습니다!");
                }else if(e.getWhoClicked().getInventory().getItemInOffHand().getItemMeta().getPersistentDataContainer().get(keyss, PersistentDataType.INTEGER) == 1){
                    econ.depositPlayer(e.getWhoClicked().getName(),p1.minecraft * e.getWhoClicked().getInventory().getItemInOffHand().getAmount());
                    e.getWhoClicked().sendMessage("성공적으로 판매하였습니다!");
                }
                e.setCancelled(true);
            }
            e.setCancelled(true);
        }else{
            return;
        }
    }
}
