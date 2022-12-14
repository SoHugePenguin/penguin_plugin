package Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin;

import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Blocks.*;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Entity.Anchor;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Entity.BaseNpc;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Entity.Car1;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Armors.boots_1;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Armors.chests_1;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Armors.helmets_1;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Armors.leggings_1;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Custom_Pickaxe;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Edibles.Fire_Pepper_Item;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Saber.sword_1;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Saber.sword_2;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Summon_egg.anchor_egg;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Summon_egg.anchor_information;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Tools.BuildGod_Item;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Tools.TheWorld_Menu_Item;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Windows.Build_Item_Win.Coordinate_sorting;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Windows.Teleport.Worlds_teleport;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.defaults.SayCommand;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.provider.CustomClassEntityProvider;
import cn.nukkit.event.Listener;
import cn.nukkit.item.Item;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.level.particle.RedstoneParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.scoreboard.data.DisplaySlot;
import cn.nukkit.scoreboard.scoreboard.Scoreboard;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Tool.ball_tool.ball;
import static Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Tool.round_tool.round;
import static Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Windows.Build_Item_Win.Build_Menu.getBuildWindow;

public class Main_PluginBase extends PluginBase implements Listener {
    public static String date_show;  //????????????
    public static ArrayList<Player> online_players = new ArrayList<>(); //??????????????????

    public static Map<String, ArrayList<Vector3>> build_map = new HashMap<>();

    public static Map<String, ArrayList<Block>> undo_map = new HashMap<>();
    static Player Plugin_player;
    static ArrayList<Player> access_p = new ArrayList<>();
    ArrayList<Vector3> v_collect = new ArrayList<>();
    Config player_join;

    public static void access(Player player) {
        access_p = new ArrayList<>();
        access_p.add(player);
        Plugin_player = player;
    }

    public void onLoad() {
//        ????????????abc
        PluginLogger log = this.getLogger();
        if (Item.fromString("np:world_menu").equals(Item.get(0))) {
            Entity.registerCustomEntity(new CustomClassEntityProvider(BaseNpc.def, BaseNpc.class));
            Entity.registerCustomEntity(new CustomClassEntityProvider(Car1.def, Car1.class));
            Entity.registerCustomEntity(new CustomClassEntityProvider(Anchor.def, Anchor.class));
            Item.registerCustomItem(List.of(Custom_Pickaxe.class, TheWorld_Menu_Item.class,
                    Fire_Pepper_Item.class, BuildGod_Item.class, sword_1.class, sword_2.class,
                    anchor_egg.class, helmets_1.class, leggings_1.class, boots_1.class, chests_1.class
            ));
            Block.registerCustomBlock(List.of(test_slab.class, block1.class, block2.class, block3.class, block4.class,
                    block5.class, block6.class, block7.class, block8.class, block9.class, block10.class,
                    block11.class, block12.class, block13.class, block14.class, block15.class, block16.class,
                    block17.class, block18.class, block19.class, block20.class, block21.class, block22.class,
                    block23.class, block24.class, block25.class, block26.class, block27.class, block28.class,
                    block29.class, block30.class, block31.class, block32.class, block33.class, block34.class,
                    block35.class, block36.class, block37.class, block38.class, block39.class, block40.class));
        } else {
            Entity.registerCustomEntity(new CustomClassEntityProvider(BaseNpc.def, BaseNpc.class));
            Entity.registerCustomEntity(new CustomClassEntityProvider(Car1.def, Car1.class));
            Entity.registerCustomEntity(new CustomClassEntityProvider(Anchor.def, Anchor.class));
            log.info("??b?????????????????????????????????Warn???????????????????????????");

            Map<UUID, Player> online_list = Server.getInstance().getOnlinePlayers();
            online_players = new ArrayList<>();
            if (online_list.size() > 0) {
                Iterator<UUID> keyIterator = online_list.keySet().iterator();
                for (int i = 0; i < online_list.size(); i++) {
                    Object key = keyIterator.next();
                    online_list.get(key).sendToast("\uE103??aReload>>>???????????????????????????", "??g  ??????BUG????????????????????????");
                }
            }
        }
    }

    public void onEnable() {
        //???????????????????????????????????????
        File BaseFile = new File("penguin_plugin");
        if (!BaseFile.exists()) {
            BaseFile.mkdirs();
            getLogger().warning("??c??????penguin_plugin????????????????????????????????????");
        }
        File file = new File("penguin_plugin", "SkyBlock_World");
        if (!file.exists()) {
            file.mkdirs();
            getLogger().warning("??c??????SkyBlock_World????????????????????????????????????");
        }
        File file2 = new File("penguin_plugin", "Builder_Save");
        if (!file2.exists()) {
            file2.mkdirs();
            getLogger().warning("??c??????Builder_Save????????????????????????????????????");
        }
        File file3 = new File("penguin_plugin", "skins");
        if (!file3.exists()) {
            file3.mkdirs();
            getLogger().warning("??c??????skins????????????????????????????????????");
        }

        Path world_data = Paths.get(Server.getInstance().getDataPath() + "penguin_plugin\\SkyBlock_World\\SkyBlock_Home.zip");
        if (!Files.exists(world_data)) {
            getLogger().warning("??c??????SkyBlock_Home.zip???????????????????????????");
            try {
                Files.copy(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("resources/SkyBlock_Home.zip")), world_data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Path re_pack = Paths.get(Server.getInstance().getDataPath() + "resource_packs\\Penguin_RE_pnx.zip");
        if (!Files.exists(re_pack)) {
            getLogger().warning("??c??????Penguin_RE_pnx.zip??????????????????????????????????????????????????????????????????");
            try {
                Files.copy(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("resources/Penguin_RE_pnx.zip")), re_pack);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Path steve_png = Paths.get(Server.getInstance().getDataPath() + "penguin_plugin\\skins\\steve.png");
        if (!Files.exists(steve_png)) {
            try {
                Files.copy(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("resources/steve.png")), steve_png);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.getServer().getPluginManager().registerEvents(new ServerListener(), this);
        this.getLogger().info("??m??6???????????????????????????????????????????????????????????????????????????");
        this.getDataFolder().mkdirs();
        this.saveDefaultConfig();
        Config player = new Config(this.getDataFolder() + "/player.yml", 2);
        this.saveResource("player.yml");

        Config npc_config = new Config("penguin_plugin/Npc_config/npcBase.yml", 2);
        if (npc_config.get("npcNumber") == null) {
            npc_config.set("npcNumber", 0);
            npc_config.set("name", "steve");
            npc_config.set("skin", "steve.png");
            npc_config.set("model_size", 1.0f);
            npc_config.set("eye_height", 1.8f);
        }
        npc_config.save();

        this.getServer().getScheduler().scheduleRepeatingTask(this, () -> {
            Date date = new Date(System.currentTimeMillis());
            date_show = date.toString();
            int data_test = 0;
            for (int i = 0; i < date_show.length(); i++) {
                if (String.valueOf(date_show.charAt(i)).equals(" ")) {
                    data_test++;
                }
                if (data_test == 3) {
                    date_show = "at " + date_show.substring(i + 1, i + 9);
                }
            }
//            ??????????????????????????????
            Map<UUID, Player> online_list = Server.getInstance().getOnlinePlayers();
            online_players = new ArrayList<>();
            if (online_list.size() > 0) {
                Iterator<UUID> keyIterator = online_list.keySet().iterator();
                for (int i = 0; i < online_list.size(); i++) {
                    Object key = keyIterator.next();
                    online_players.add(online_list.get(key));
                }
            }
//            reload???????????????????????????????????????
            if (build_map.size() == 0) {
                for (Player online_player : online_players) {
                    build_map.put(online_player.getName(), new ArrayList<>());
                }
            }

            if (undo_map.size() == 0) {
                for (Player online_player : online_players) {
                    undo_map.put(online_player.getName(), new ArrayList<>());
                }
            }


            if (online_players.size() > 0) {
                String TargetBlock, Biomes;
                int ID, ID_DATA;
                for (Player p : online_players) {
                    if (p.getTargetBlock(5) == null) {
                        TargetBlock = "Air";
                        ID = 0;
                        ID_DATA = 0;
                    } else {
                        TargetBlock = p.getTargetBlock(5).getName();
                        ID = p.getTargetBlock(5).getBlock().getId();
                        ID_DATA = p.getTargetBlock(5).getExactIntStorage();
                    }

                    byte[] BiomesList = Objects.requireNonNull(p.getChunk()).getBiomeIdArray();
                    int list_idr = (int) (p.getX() - p.getChunkX() * 16) * 16 + (int) (p.getZ() - p.getChunkZ() * 16);
                    if (BiomesList[list_idr] < 0) {
                        Biomes = Biome.getBiome(p.getLevel().getBiomeId(p.getChunkX(), p.getChunkZ())).getName();
                    } else {
                        Biomes = Biome.getBiome(BiomesList[list_idr]).getName();
                    }
                    //???????????????????????????
                    Scoreboard showInfo = new Scoreboard("playerInfo", "??o??6mc.zj.cn");
                    List<String> line = new ArrayList<>();
                    line.add("??b????????? ??f" + Biomes + " ");
                    line.add("??aPing??? ??f" + p.getPing() + "ms ");
                    line.add("??c????????? ??f" + p.getLevelName() + " ");
                    line.add("??d????????? ??f" + online_players.size() + " / " + p.getServer().getMaxPlayers());
                    line.add("??e????????? ??f0 ");
                    showInfo.setLines(line);

                    p.removeScoreboard(showInfo);
                    p.display(showInfo, DisplaySlot.SIDEBAR);

                    showInfo.removeAllLine(false);
                    showInfo.resend();
                    //resend();?????????????????????????????????????????????????????????
                    //???????????????????????????????????????????????????????????????????????????????????????

                    p.sendTip("??c?????????" + TargetBlock + " (" + ID + ":" + ID_DATA + ")"
                            + "    ??b?????????" + p.getInventory().getItemInHand().getName() + " ID???" + p.getInventory().getItemInHand().getId());
                }
            }

            long i = player.getLong("all");
            player_join = player;
            if (Plugin_player == null) {
                if (player.getLong("all") == 0L) {
                    player.set("all", 1);
                    player.set("isFirst", false);
                    player.save();
                }
            } else {
                boolean test = false;

                for (int j = 0; (long) j < i; ++j) {
                    if (!Plugin_player.getName().equals(player.getString(String.valueOf(j)))) {
                        if (!player.getBoolean("isFirst")) {
                            player.set(String.valueOf(j), Plugin_player.getName());
                            player.set("isFirst", true);
                            player.save();
                            test = true;
                        }
                    } else {
                        test = true;
                    }

                    if (!test && (long) j == i - 1L) {
                        player.set(String.valueOf(j + 1), Plugin_player.getName());
                        player.set("all", j + 2);
                        player.save();
                    }
                }
            }

            //????????????????????????//
            Player WoodAxePlayer;
            ArrayList<Vector3> v;
            v_collect = new ArrayList<>();
            for (Player online_player : online_players) {
                WoodAxePlayer = online_player;
                v = build_map.get(online_player.getName());
                if (v != null && v.size() == 2 && v.get(0) != null && v.get(1) != null) {
                    Coordinate_sorting.sorting(v);
                    for (double k1 = v.get(0).x; k1 < v.get(1).x + 2; k1++) {
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(k1, v.get(0).y, v.get(0).z)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(k1, v.get(1).y + 1, v.get(0).z)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(k1, v.get(0).y, v.get(1).z + 1)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(k1, v.get(1).y + 1, v.get(1).z + 1)));
                    }
                    for (double k2 = v.get(0).y; k2 < v.get(1).y + 2; k2++) {
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(0).x, k2, v.get(0).z)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(1).x + 1, k2, v.get(0).z)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(0).x, k2, v.get(1).z + 1)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(1).x + 1, k2, v.get(1).z + 1)));
                    }
                    for (double k3 = v.get(0).z; k3 < v.get(1).z + 2; k3++) {
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(0).x, v.get(0).y, k3)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(0).x, v.get(1).y + 1, k3)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(1).x + 1, v.get(0).y, k3)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(1).x + 1, v.get(1).y + 1, k3)));
                    }
                }
            }

        }, 10);
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if (sender.isPlayer()) {
            Player p = (Player) sender;
            if (command.getName().equals("get")) {
                boolean NewPlayer = true;
                if (args.length == 1 && args[0].equals("1")) {
                    HashSet<String> achievements = p.achievements;
                    String[] NewList = achievements.toArray(new String[0]);
                    int var10 = NewList.length;

                    int j;
                    for (j = 0; j < var10; ++j) {
                        String s = NewList[j];
                        if (s.equals("???????????????")) {
                            p.sendMessage("??c??m<Server>?????????????????????????????????,?????????");
                            NewPlayer = false;
                            break;
                        }
                    }

                    if (NewPlayer) {
                        p.achievements.add("???????????????");
                        SayCommand.broadcastCommandMessage(p, "??a?????????????????????");
                        StringBuilder item_all_name = new StringBuilder(" ");
                        int[] item_id = new int[]{46, 1, 267, 1, 298, 1, 299, 1, 300, 1, 301, 1, 322, 3, 426, 1};

                        for (j = 0; j < item_id.length; j += 2) {
                            Item item = Item.fromString(String.valueOf(item_id[j]));
                            item.setCount(item_id[j + 1]);
                            p.getInventory().addItem(item.clone());
                            item_all_name.insert(0, "??b??m" + item.getName() + "x" + item.getCount() + "\n");
                        }

                        p.sendMessage("??d??m" + date_show + "\n??6??m" + p.getName() + ", ???????????????");
                        p.sendMessage(String.valueOf(item_all_name));
                    }
                } else {
                    sender.sendMessage("??m??6?????????????????????????????????\n??b??????/get 1???????????????????????????\nQQ???:793186488");
                }
            }

            if (command.getName().equals("do")) {
                p.showFormWindow(getBuildWindow());
            }

            if (command.getName().equals("biomes")) {
                //player.onChunkChanged(FullChunk c)  ??????????????????????????????
                int x = p.getChunkX(), z = p.getChunkZ();
                if (args.length == 1 && !Objects.equals(args[0], "list")) {
                    byte id = Byte.parseByte(args[0]);
                    for (int i = 0; i < 16; i++) {
                        for (int j = 0; j < 16; j++) {
                            Objects.requireNonNull(p.getChunk()).setBiome(i, j, Biome.getBiome(id));
                            p.getChunk().setBiomeId(i, j, id);
                        }
                    }
                    //p.getServer().getLevel(p.getLevel().getId()).generateChunk(x,z);  //????????????
                    p.onChunkChanged(p.getChunk());
                } else if (args.length == 1 && args[0].equals("list")) {
                    p.sendMessage(Arrays.toString(Objects.requireNonNull(p.getChunk()).getBiomeIdArray()));
                    StringBuilder BiomesList = new StringBuilder();
                    for (int i = 0; i < Biome.unorderedBiomes.size(); i++) {
                        BiomesList.append("??6[").append(i).append("]??a").append(Biome.getBiome(i).getName());
                    }
                    p.sendMessage("??b???????????????\n" + BiomesList);
                    p.sendMessage("??????????????????????????? " + Biome.getBiome(p.getLevel().getBiomeId(x, z)).getName());
                } else
                    p.sendMessage("??a/Biomes list ??????????????????????????????ID\n??b/Biomes [ID] ?????????????????????????????????id???????????????");
            }

            if (command.getName().equals("round")) {
                if (args.length == 1) {
                    p.sendMessage("????????????????????????????????????????????????????????????");
                } else if (args.length > 1) {
                    p.sendMessage("??c??????????????????????????????????????????????????????????????????OP!??????????????????");
                    round(p, args);
                } else
                    p.sendMessage("?????????????????????/round ??????(???????????????) ??????ID(??????) ???????????????????????????keep(?????????????????????????????????????????????????????????)");
            }

            if (command.getName().equals("ball")) {
                if (args.length == 1) {
                    p.sendMessage("????????????????????????????????????????????????????????????");
                } else if (args.length > 1) {
                    p.sendMessage("??c??????????????????????????????????????????????????????????????????OP!??????????????????");
                    SayCommand.broadcastCommandMessage(sender, "??a" + p.getName() + "?????????" + Arrays.toString(args));
                    ball(p, args);
                } else
                    p.sendMessage("?????????????????????/ball ??????(???????????????) ??????ID(??????) ???????????????????????????keep(?????????????????????????????????????????????????????????)");
            }

            if (command.getName().equals("zoom") && args.length == 2) {
                p = this.getServer().getPlayer(args[0]);
                float a = Float.parseFloat(args[1]);
                if ((double) a >= 0.5 && a <= 3.0F) {
                    p.setScale(a);
                    sender.sendMessage("??a??mSuccess!");
                    p.sendMessage("??b??m???????????????????????????");
                } else {
                    sender.sendMessage(date_show + "??m??c<Error>???????????????0.5~3?????????");
                }
            }

            if (command.getName().equals("clear")) {
                if (args.length == 0) {
                    p.getInventory().clearAll();
                    p.sendMessage("??b??????????????????");
                } else if (args.length == 1) {
                    p = this.getServer().getPlayer(args[0]);
                    if (p == sender) {
                        p.getInventory().clearAll();
                        sender.sendMessage("??a??m Success");
                        p.sendMessage("??b??????????????????");
                    } else if (p == null) {
                        sender.sendMessage("??m??c<Error>?????????????????????????????????????????????");
                    } else {
                        p.getInventory().clearAll();
                        sender.sendMessage("??a??m Success");
                        p.sendMessage("??b??????????????????" + sender.getName() + " ?????????");
                    }
                } else {
                    sender.sendMessage("??d??m/clear  ??????????????????\n/clear [name] ????????????????????????");
                }
            }

            if (command.getName().equals("ping")) {
                p = (Player) sender;
                String device;
                if (args.length == 0) {
                    if (p.getLoginChainData().getDeviceOS() == 7) {
                        device = "PC???";
                    } else {
                        device = "?????????";
                    }
                    p.sendMessage("??b??mPort: " + p.getPort() + "??a\nPing: " + p.getPing() + "??6ms\n????????? " +
                            device + " ?????????????????????");
                } else {
                    p.sendMessage("??m/ping");
                }

                Car1 anchor = new Car1(p.getLocation().getChunk(), Entity.getDefaultNBT(p.getPosition())
                        .putString("account", "null")
                        .putCompound("Skin", (new CompoundTag()))
                );
                anchor.spawnToAll();
                p.sendMessage(String.valueOf(anchor_information.name));
                p.sendMessage(String.valueOf(anchor_information.vector3));
            }

            if (command.getName().equals("achievement")) {
                p = (Player) sender;
                if (args.length == 0) {
                    p.sendMessage("??g??m??????????????????:\n" + ((Player) sender).achievements);
                } else if (args.length == 2 && args[1].equals("clear")) {
                    p = this.getServer().getPlayer(args[0]);
                    if (p == sender) {
                        p.achievements.clear();
                        p.sendMessage("??b????????????????????????");
                    } else if (p == null) {
                        sender.sendMessage("??m??c" + date_show + "<Error>?????????????????????????????????????????????");
                    } else {
                        p.achievements.clear();
                        sender.sendMessage("??a??m Success");
                        p.sendMessage("??b???????????????????????? " + sender.getName() + " ?????????");
                    }
                } else if (args.length == 3 && args[1].equals("add")) {
                    p = this.getServer().getPlayer(args[0]);
                    if (p == sender) {
                        p.achievements.add(args[2]);
                        p.sendMessage("??b?????? " + args[2] + " ????????????");
                    } else if (p == null) {
                        sender.sendMessage("??m??c" + date_show + "<Error>?????????????????????????????????????????????");
                    } else {
                        p.achievements.add(args[2]);
                        sender.sendMessage("???????????????");
                        p.sendMessage("??b" + sender.getName() + "??????????????? " + args[2] + " ??????");
                    }
                } else if (args.length == 3 && args[1].equals("remove")) {
                    p = this.getServer().getPlayer(args[0]);
                    if (p == sender) {
                        p.achievements.remove(args[2]);
                        p.sendMessage("??b?????? " + args[2] + " ????????????");
                    } else if (p == null) {
                        sender.sendMessage("??m??c" + date_show + "<Error>?????????????????????????????????????????????");
                    } else {
                        p.achievements.remove(args[2]);
                        sender.sendMessage("???????????????");
                        p.sendMessage("??b" + sender.getName() + "??????????????? " + args[2] + " ??????");
                    }
                } else {
                    sender.sendMessage("??d??m/achievement                   ???????????????????????????\n/achievement [?????????] clear          ??????????????????\n/achievement [?????????] add [??????]     ????????????(??????)\n/achievement [?????????] remove [??????]  ????????????(??????)\n??a----------------------");
                }
            }

            if (command.getName().equals("set")) {
                sender.sendMessage("????????????/do");
            }

            if (command.getName().equals("w")) {
                assert p != null;
                Worlds_teleport.WorldTeleport(p);
            }

            if (command.getName().equals("map")) {
                assert p != null;
                //?????????????????????
                p.sendPopup("aaa");
            }
        } else {
            sender.sendMessage(TextFormat.colorize('&', "&o&c???????????????????????????"));
        }

        return true;
    }
}
