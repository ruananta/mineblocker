# MineBlocker от mor_iv
MineBlocker плагин для сервера Bukkit MineCraft [CB 1.6.* - 1.11.*]

## Описание

MineBlocker - запреты взаимодействия предметами с миром.​
Версия: v_0.2.5 - бесплатная, с урезанным функционалом. СКАЧАТЬ
Версия: v_2.3.6 - приватная, 300р со всеми будущими обновлениями.
Все нюансы работы плагина прописаны в ChangeLog.
Все что будет подчеркнуто, то это может только приватная версия.​

Данный плагин работает как ModifyWorld, но более гибкий и поддерживает плагины приватов и групп. Поддерживает моды (за редким исключением).
Умеет запрещать:
- установку блока игроком
- установку блоков рядом друг с другом
- ломать блок игроком
- правый/левый клик мышью с предметом в руке
- правый клик/shift+Правый клик по блоку или энтити
- правый/левый клик по предмету в инвентаре
- подбирать предмет
- выбрасывать предмет
- хранить в инвентаре предмет, так же может проверять по времени
- передвигать по инвентарю предмет
- бить энтити
- ванильный крафт
- операции с ведром(вылить, наполнить)
- открывать блок
- открывать блок двум и более игрокам одновременно
- наступать на пластины
- поршням двигать предметы
так же умеет:
- проверять предмет на превышение максимального количества в слоте
- взаимодействовать с WorldGuard
- взаимодействовать с PreciousStones
- назначать каждому запрету свое сообщение
И имеет:
- собственный Permissions лист.
- поддержку ID или(и) NAME ++ meta
- поддержку NBT тегов (крайне не заменимо в модах)
- настройку (поддержку) предметов с большой дальностью кликов

П.С. - умеет запрещать в чужом регионе, возле чужого региона с фиксируемым расстоянием, так и за пределами своего. Так же может игнорировать OP ("*") игрока.
Так же в PermissionsList можно устанавливать права в определенных регионах, мирах, группах и режимах игры.

В PermissionsList можно:
1) создавать группы запретов, которые действуют глобально или только в:
- мирах
- группах PermissionsEx или любых других плагинов которые поддерживают Vault(необходим vault)
- определенных регионах WG/PS (по именам)
- режимах игры
- так же парами или все сразу. пример(только для группы, только в этом мире, только в этом регионе и этом режиме игры)
2) распределять запреты на 4 вида:
- действуют везде
- действуют только в чужом регионе (на конце ".wg")
- действуют в чужом регионе и возле него (на конце ".wg|расстояние")
- действуют только за пределами своего региона (на конце ".out")
3) вписывать как запреты так и разрешения
4) назначить свои сообщения для каждого запрета

## Отслеживаемые действия
```bash
Можно вписать в плагин привилегий или в PermissionsList.
[itemName] - предмет в руке
[blockName] - блок или этити
[inventoryName] - имя инвентаря

- place.[blockName] - установка блока.
- place_near.[blockName]|[blockName]|[blockName] - установка блоков рядом.
- break.[blockName] - разрушение блока
- have.[itemName] - ношение предмета
- have_r.[itemName] - ношение предмета с удалением
- packup.[itemName] - подбор предмета
- drop.[itemName] - выбрасывание предмета
- left_click_air.[itemName] - левый клик предметом в воздухе
- shift_left_click_air.[itemName] - левый клик + shift с предметом в воздухе
- left_click_block.[itemName].[blockName] - левый клик предметом по блоку
- shift_left_click_block.[itemName].[blockName] - левый клик + shift с предметом по блоку
- right_click_air.[itemName] - правый клик предметом в воздухе
- shift_right_click_air.[itemName] - правыйклик + shift с предметом в воздухе
- right_click_block.[itemName].[blockName||entityName]- правый клик предметом по блоку или энтити
- shift_right_click_block.[itemName].[blockName||entityName] - правый клик + shift с предметом по блоку или энтити
- physical.[blockName] - выжим пластины на полу
- opening.[itemName].[blockName] - открытие инвентаря
- damage.[entityName] - урон по энтити
- double_opening.[blockName] - открытие инвентаря двумя и более игроками
- craft.[itemName] - крафт в ванильных верстаках
- bucket_fill.[fluidName] - наполнение ведра
- bucket_empty.[bucketName] - выливание ведра
- piston_extend.[blockName] - толкание поршнем(без привязки к игроку)
- piston_retract.[blockName] - притягивание поршнем(без привязки к игроку)

Клики по предметам в инвентаре:
- invclick_creative.[itemName]
- invclick_creative.[itemName].[inventoryName]
- invclick_left.[itemName]
- invclick_left.[itemName].[inventoryName]
- invclick_shift_left.[itemName]
- invclick_shift_left.[itemName].[inventoryName]
- invclick_right.[itemName]
- invclick_right.[itemName].[inventoryName]
- invclick_shift_right.[itemName]
- invclick_shift_right.[itemName].[inventoryName]
- invclick_middle.[itemName]
- invclick_middle.[itemName].[inventoryName]
- invclick_number_key.[itemName]
- invclick_number_key.[itemName].[inventoryName]
- invclick_double.[itemName]
- invclick_double.[itemName].[inventoryName]
- invclick_drop.[itemName]
- invclick_drop.[itemName].[inventoryName]
- invclick_control_drop.[itemName]
- invclick_control_drop.[itemName].[inventoryName]
- invclick_unknown.[itemName]
- invclick_unknown.[itemName].[inventoryName]

Окончания:
- private или wg - правило работает только в чужом регионе
- private:X или wg:X - правило работает в чужом регионе или возле него на расстоянии "X"
- out - правило работает за пределами своего региона
- message:Сообщение или msg:Сообщение - возможность назначения сообщения только для этого правила
- mute - данный запрет не будет писать в чат
- remove или rm - удаление предмета из рук на 1 тик
- remove:X или rm:X - удаление предмета из рук на X тиков
```

- MineBlockerUtils.bypass - выключение проверки игрока по PermList. это работает в случае настройки в конфиге "op-bypass-permissions-list: true"

Если на окончание добавить .private или .wg - то действие работает только в том регионе, в котором игрок не записан в хозяины или жители региона.
Если добавить .private:расстояние, то действие работает не только в регионе, но и возле него.
Пример
```bash
- -packup.*.wg - запрет подбора всех предметов в чужом регионе
- -rightclickon.*.wg - запрет правого клика с любым предметом по любому блоку в чужом регионе
- -packup.*.wg:3 - запрет подбора всех предметов в чужом регионе и на расстоянии в 3 блока от него
```
Если на окончание добавить .out - то действие работает только за пределами "своего" региона. "Свой регион" - это в котором игрок является хозяином или жителем.
Пример
```bash
- -packup.*.out- запрет подбора всех предметов за пределами своего региона
- -rightclickon.*.out - запрет правого клика с любым предметом по любому блоку за пределами своего региона
```
П.С. Смотрим окончания в действиях....

## Команды

Доступ к командам перм: MineBlockerUtils.operator    
/mb info (i)- узнать имя предмета, блока, энтити рядом с блоком(если оно там есть)    
/mb inventory (inv) - узнать имя предмета, блока, энтити рядом с блоком(если оно там есть)    
/mb reload (r) - перезагрузка плагина(в случает включения/выключения слушателей(listener), необходима перезагрузка сервера    
/mb permissionslist|plist|pl [имя секции] add|remove group|region|world|permission|gamemode [значение] - команда которая добавляет / удаляет из секции группу / регион / мир / право / режим игры. Полностью поддерживает кнопку [Tab] от начала до конца. Ставить надо только пробелы. если с permisson , то там еще и точки. Пример /mb plist test_1 add permission -drop.stone:* добавит запрет выбрасывания камня в секции test_1

## Правильные запреты

Плагин блокирует только то, что ему указали. Так если запретить ношение предмета, не будет запрещен его подбор, так же если запретить "клик в воздухе", то не будет запрещен "клик по блоку" и "shift + клик по блоку". Что бы обойти это надо писать запреты правильно. Примеры c камнем:
```bash
- -have|packup.stone
- -rightclick|rightclickon|shift_rightclickon.stone
```
Так же если вы хотите полностью заблокировать предмет, то в PL надо вписать - -mineblocker.*.stone

## Конфиг

```bash
#включены ли сообщения
enable-message: true
#язык сообщений - "ru" или "en"
message-language: en
#задержка между одинаковыми сообщениями игроку
message-cooldown-time-ms: 1000
#работа с именами или с id
enable-itemName(true-name,false-id): true
#учёт meta у предмета
enable-metadata: true
#включены ли NBT теги предметов/блоков
enable-NBT-tags: false
#использование WorldGuard (если false то PreciousStones)
use-WorldGuard: true
#проверка OP в привате. Не даёт OP игнорировать приват в запретах.
enable-check-op-in-private: true
#работа по плагину разрешений(PEX и т.п.) НЕ СТАБИЛЬНО!!!!
enable-check-player-permissions: false
#включен ли PermissionsList
enable-permissions-list: true
#обход OP PermissionsList либо по перму
op-bypass-permissions-list: false
#черный лист у поршней
piston-black-list: true
#проверка ношения(have & have_r) по времени в секундах
have-task-timer-time: 0
#проверка ношения при КАЖДОМ включенном действии. Не рекомендую.
have-listener:
  break: false
  place: false
  drop: false
  packup: false
  craft: false
#проверка максимального размера стака
  max-stack-size-check: true
  damage: false
  inv_click: false
  left_click: false
  right_click: false
  bucket_empty: false
  bucket_fill: false
```

## PermissionsList

В листе можно как запретить, так и разрешить.    
Запрет "- -", разрешение "- ", можно записать несколько значений типа права и предмета в строку через "|", предмету можно дать NBT тег. Так же добавлены такие понятия как не пустой NBT {!{}} и пустой NBT {{}} тег.    
Примеры: dirt:0{1, 2, 3, !4, !5, +6, +7} земля c NBT: Включает 1 или 2 или 3, так же не включает 4 и 5, так же включает 6 и 7;    
dirt{!{}} земля с не пустым NBT;    
dirt{{}} земля с пустым NBT тегом;    
PermissionsList претерпел много изменений. Теперь он поделен на секции, которые не зависят друг от друга. Секций может быть сколько угодно и имя секции влияет только на доступ к ней командой.     
Важно: имена секций не должны повторяться.
Ниже секция test_1 действует для всех, во всех мирах.    
Если добавить список:    
worlds: то будет действовать только в мирах из списка;    
groups: то только для групп;    
regions: то только в регионах из списка;    
gamemodes: то только если игрок находится в режиме игры из списка.    
Если все сразу, то только для групп в мирах, в регионах и в режимах игры с именами из списков.    
Лист отлично понимает "*" в имени предметов, в начале права не надо писать "mineblocker.". Если опустить значение мета, то будет взята мета "*".    
Примеры: drop|packup.dirt|wool:3.wg | invclick|rightclickon|shift_rightclickon.*{!{}}.item_frame    
Так же если у игрока забрать право, а потом вернуть в другой секции, то у него не будет запрета. Попрошу внимательно к этому отнестись.    

```bash
test_1:
  permissions:
  - -drop.stone:0.wg
  - -craft.stick:0.out
  - -rightclickon.dirt:0.item_frame
  - -have.*{ggg}
  - -place|drop.stone:0|dirt:0
  - -invclick_left.stone
test_2:
  worlds:
  - world
  - nether
  regions:
  - spawn
  - mine
  groups:
  - pvp
  - vip
  gamemodes:
  - creative
  - survival
  permissions:
  - drop.stone:0
  - rightclickon.iron_pickaxe.furnace.wg:5
```
Введена возможность назначить каждому запрету свое сообщение.
На конец запрета надо добавить ".msg:Сообщение" без кавычек, а после него писать свое сообщение.
Плагин понимает все эти варианты. Online YAML Parser в помощь. Не забываем про кодировку UTF-8:
```bash
sample:
  permissions:
  - -packup.stone.msg
    &aВы не можете подбирать &6камень &aпотому, что сервер так решил.
  - -drop.stone:0.msg&aВы не можете выбрасывать &6камень &aпотому, что у вас злая администрация.
  - -damage.villager.msg
    &4Я супер житель,
    ты не можешь
    меня бить.
```
## PistonBlackList
Черный список блоков у поршней. Имеет поддержку NBT. Настраивается по каждому миру или по всем сразу.
```bash
AllWorlds:
- sample
- sample:0
- sample:0{nbt}
world:
- '*{!{}}'
- sample:0
- sample:0{nbt}
```

## CustomItems CustomPermissions
В этих файлах можно переназначать имена и делать объединения предметов / блоков / разрешений. Потом эти имена можно использовать в правилах установки блоков рядом друг с другом и в PermissionsList.
Имена предметов поддерживают мета. Если не указать то "имя" = "имя:*"
Так же поддерживают NBT. записать предмет можно так же как - '*{!{}}' что эквивалентно "любой предмет/блок с не пустым NBT тегом". Одинарные кавычки обязательны из-за правил заполнения yml.
Важно: Что бы все работало нормально с русским языком, нужно что бы все файлы и символы были в кодировке UTF-8(Без BOM)!!!
#### CustomItems
```bash
all_swords:
- wood_sword
- stone_sword
- iron_sword
- gold_sword
- diamond_sword
all_pickaxe:
- wood_pickaxe
- stone_pickaxe
- iron_pickaxe
- gold_pickaxe
- diamond_pickaxe
поршень:
- piston_base
- piston_sticky_base
```
#### CustomPermissions
```bash
правый_клик:
- rightclick
- rightclickon
- shift_rightclickon
левый_клик:
- leftclick
- leftclickon
custom_packup:
- packup
- have
full_ban:
- place
- break
- packup
- have
- damage
- craft
- rightclick
- rightclickon
- shift_rightclickon
- leftclick
- leftclickon
```
#### Примеры использования этих прав и имен:
```bash
test:
  permissions:
  - -правый_клик|левый_клик.all_swords|all_pickaxe.поршень.wg
  - -custom_packup.sponge
  - -full_ban.stone
  - -place.поршень
```

## RangeItems
Список дальнобойных предметов. Записи в столбик, NBT+.
Писать в виде "предмет|дистанция"
```bash
sample|20
sample:0|20
sample:0{nbt}|30
```
## Replacer.txt
Переименовывание запрещенного предмета в сообщении игроку. (пока нет поддержки NBT и цветов)
```bash
stone:0|Супер Камень
wood:0|Железное дерево
```
## WorldGuard-ignore-regions-list.yml
Листы регионов по мирам, в которых не надо проверять запреты. Например автошахта.
Миров и регионов можно вносить сколько угодно.
```bash
world:
- mine_car
```
Добавит в игнор регион с именем mine_car в мире world.

## message_ru
Тут хранятся сообщения плагина. Все ключи сообщений идентичны именам запретов. Он хранит типовые сообщения и изменяет у них окончания и мена предметов в зависимости от ситуации.
